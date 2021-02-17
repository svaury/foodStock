package com.example.foodstock.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodstock.repository.ProductRepository
import org.koin.java.KoinJavaComponent.inject

class ProductStockViewModel(val productRepository : ProductRepository): ViewModel() {


}