package com.example.foodstock

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.foodstock.repository.DbHelper
import com.example.foodstock.repository.ProductRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(applicationModule))
        }
    }



}
