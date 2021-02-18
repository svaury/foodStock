package com.example.foodstock

import androidx.room.Room
import com.example.foodstock.repository.DbHelper
import com.example.foodstock.repository.ProductRepository
import com.example.foodstock.repository.remote.GetProductService
import com.example.foodstock.ui.viewmodel.AddProductViewModel
import com.example.foodstock.ui.viewmodel.ProductStockViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {

    single { Room.databaseBuilder(
        androidContext(),
        DbHelper::class.java, "database-musics"
    ).build()}


    single{ Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        get<Retrofit>().create(GetProductService::class.java)
    }


    single { get<DbHelper>().productDao() }

    single { ProductRepository(get(),get())}

    viewModel { ProductStockViewModel(get()) }

    viewModel { AddProductViewModel(get()) }
}
