package com.touchin.prosto.util

object EmailUtil {

    private const val BASE_SYMBOLS = "a-zA-Z0-9"

    private const val LOCAL_PART_PATTERN: String = "^[$BASE_SYMBOLS\\+\\.\\_\\%\\-]"
    private const val DOMAIN_PART_PATTERN: String = "\\@[[^\\-.]&&$BASE_SYMBOLS\\.][$BASE_SYMBOLS\\-]"
    private const val HOST_PATTERN: String = "\\.[$BASE_SYMBOLS]"

    // Pattern for email validation
    // 1. local-part [a-zA-Z0-9\+\.\_\%\-]{1,64}
    // - uppercase and lowercase Latin letters A to Z and a to z
    // - digits 0 to 9
    // - printable characters - _ % + - .
    // from 1 to 64 symbols
    private const val LOCAL_PART_EMAIL: String = "$LOCAL_PART_PATTERN{1,64}"

    // 2. domain-part [a-zA-Z0-9][a-zA-Z0-9\-]{1,64}
    // - uppercase and lowercase Latin letters A to Z and a to z
    // - digits 0 to 9 and '-' not in the beginning
    // from 2 to 64 symbols
    private const val DOMAIN_PART_EMAIL: String = "$DOMAIN_PART_PATTERN{1,63}"

    // 3. (.[a-zA-Z0-9][a-zA-Z0-9\-]{2,24}) group repeats 1 or more times
    // - dot and uppercase and lowercase Latin letters A to Z and a to z
    // - digits 2 to 9
    // from 3 to 25 symbols (включая точку)
    private const val HOST_EMAIL: String = "$HOST_PATTERN{2,24}$"

    val EMAIL_PATTERN_REGEX = "$LOCAL_PART_EMAIL$DOMAIN_PART_EMAIL$HOST_EMAIL".toRegex()

}
