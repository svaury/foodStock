package com.example.foodstock.repository.mapper

import com.example.foodstock.model.Product
import com.example.foodstock.repository.local.entity.ProductEntity

class ToProductEntityMapper{


    fun entityToProductModel(product: Product): ProductEntity {
        return ProductEntity(product.id, product.barCode, product.name,product.imageUrl,product.peremptionDate)

    }
}