package com.touchin.prosto.di.modules

import android.content.Context
import com.touchin.data.api.OffersApi
import com.touchin.data.api.utils.RetrofitFactory
import com.touchin.data.api.utils.Api
import com.touchin.data.api.utils.BaseClientFactory
import com.touchin.data.api.utils.InterceptorFactory
import com.touchin.data.api.utils.MoshiFactory
import com.touchin.prosto.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideMoshiFactory() = MoshiFactory()

    @Provides
    internal fun provideInterceptorFactory(
        context: Context,
    ): InterceptorFactory = InterceptorFactory(context)

    @Provides
    internal fun provideAuthZoneApi(
        interceptorFactory: InterceptorFactory,
        moshiFactory: MoshiFactory,
    ): Api<OffersApi> {

        val clientFactory = BaseClientFactory(
            interceptors = interceptorFactory.getNonAuthZoneInterceptor(),
        )

        val retrofitFactory = RetrofitFactory(
            moshiFactory = moshiFactory,
            clientFactory = clientFactory
        )

        return Api(BuildConfig.BASE_URL, retrofitFactory, OffersApi::class.java)
    }

}
