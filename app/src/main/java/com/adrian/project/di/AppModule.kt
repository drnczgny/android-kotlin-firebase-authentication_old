package com.adrian.project.di

import com.adrian.project.MyApplication
import com.adrian.project.data.ApiService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by cadri on 2017. 08. 03..
 */

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(myApplication: MyApplication) = myApplication

    @Singleton
    @Provides
    fun provideApiService() = ApiService()

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}