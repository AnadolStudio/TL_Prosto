package com.touchin.prosto.di

import android.content.Context
import com.touchin.prosto.App
import com.touchin.prosto.di.modules.AppModule
import com.touchin.prosto.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent : SharedComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): AppComponent
    }

    fun inject(entry: App)
}
