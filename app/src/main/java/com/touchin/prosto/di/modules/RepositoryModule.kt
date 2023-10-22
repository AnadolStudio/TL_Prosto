package com.touchin.prosto.di.modules

import android.content.Context
import android.content.res.Resources
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import com.touchin.data.api.OffersApi
import com.touchin.data.api.utils.Api
import com.touchin.data.repository.common.NightModeRepositoryImpl
import com.touchin.data.repository.common.PreferenceRepositoryImpl
import com.touchin.data.repository.common.PreferencesStorage
import com.touchin.data.repository.common.ResourceRepositoryImpl
import com.touchin.data.repository.offers.OffersRepositoryImpl
import com.touchin.domain.repository.common.NightModeRepository
import com.touchin.domain.repository.common.PreferenceRepository
import com.touchin.domain.repository.common.ResourceRepository
import com.touchin.domain.repository.offers.OffersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun providePreferences(context: Context): Preferences =
        BinaryPreferencesBuilder(context)
            .allowBuildOnBackgroundThread()
            .build()

    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    fun providePreferencesStorage(preferences: Preferences): PreferencesStorage =
        PreferencesStorage(preferences)

    @Provides
    @Singleton
    fun provideNightModeRepository(
        resources: Resources,
        preferences: PreferencesStorage
    ): NightModeRepository =
        NightModeRepositoryImpl(resources, preferences)

    @Provides
    fun provideResourceRepository(context: Context): ResourceRepository =
        ResourceRepositoryImpl(context)

    @Provides
    fun providePreferenceRepository(preferences: PreferencesStorage): PreferenceRepository =
        PreferenceRepositoryImpl(preferences)

    @Provides
    fun provideOffersRepository(api: Api<OffersApi>): OffersRepository = OffersRepositoryImpl(api.provide())
}
