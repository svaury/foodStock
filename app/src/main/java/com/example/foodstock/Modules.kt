package com.example.foodstock

import androidx.room.Room
import com.example.foodstock.repository.DbHelper
import com.example.foodstock.repository.ProductRepository
import com.example.foodstock.ui.viewmodel.ProductStockViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {

    single { Room.databaseBuilder(
        androidContext(),
        DbHelper::class.java, "database-musics"
    ).build()}

    single { get<DbHelper>().productDao() }

    single { ProductRepository(get())}

    viewModel { ProductStockViewModel(get()) }

}
