package com.touchin.prosto.di.modules

import dagger.Module

@Module(
    includes = [
        RepositoryModule::class,
        NetworkModule::class
    ]
)
class AppModule
