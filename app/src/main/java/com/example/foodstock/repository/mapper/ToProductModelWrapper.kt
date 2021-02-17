package com.example.foodstock.repository.mapper

import com.example.foodstock.model.Product
import com.example.foodstock.repository.local.entity.ProductEntity

class ToProductModelWrapper{

    fun entityToProductModel(entity: ProductEntity): Product{
        return Product(entity.id,entity.barCode,entity.name,entity.thumbnailUrl,entity.peremptionDate)

    }
}