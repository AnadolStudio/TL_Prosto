package com.touchin.data.api.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import timber.log.Timber
import java.lang.reflect.Type

class SkipBadElementsListAdapter(
        private val elementAdapter: JsonAdapter<Any?>
) : JsonAdapter<List<Any?>>() {

    companion object {
        private const val TAG = "SkipBadElementsListAdapter"
    }

    object Factory : JsonAdapter.Factory {

        override fun create(
                type: Type,
                annotations: Set<Annotation>,
                moshi: Moshi
        ): JsonAdapter<*>? {
            if (annotations.isNotEmpty() || Types.getRawType(type) != List::class.java) {
                return null
            }

            val elementType = Types.collectionElementType(type, List::class.java)
            val elementAdapter = moshi.adapter<Any?>(elementType)

            return SkipBadElementsListAdapter(elementAdapter)
        }
    }

    override fun fromJson(reader: JsonReader): List<Any?>? {
        val result = mutableListOf<Any?>()

        try {
            reader.beginArray()
        } catch (e: JsonDataException) {
            //if we trying to read array but an error occurs in beginArray(), it means there is no symbol "[" -> no array, check for null
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull<String>()
                return null
            }
            throw e
        }
        while (reader.hasNext()) {
            try {
                val peeked = reader.peekJson()
                result += elementAdapter.fromJson(peeked)
            } catch (ignored: JsonDataException) {
                // Игнорируем невалидные ошибки и пишем в лог
                Timber.tag(TAG).e(ignored)
            } catch (ignored: IllegalStateException) {
                //TODO: Swallow exception "peek source is invalid because upstream source was used"
                Timber.tag(TAG).e("swallowed IllegalStateException: ${ignored.message}")
            }

            reader.skipValue()
        }
        reader.endArray()

        return result
    }

    override fun toJson(writer: JsonWriter, value: List<Any?>?) {
        if (value == null) throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")

        writer.beginArray()
        for (i in value.indices) {
            elementAdapter.toJson(writer, value[i])
        }
        writer.endArray()
    }
}
