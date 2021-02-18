package com.example.foodstock.repository.mapper

import com.example.foodstock.model.Product
import com.example.foodstock.repository.local.entity.ProductEntity
import com.example.foodstock.repository.remote.ProductModelJson

class ToProductModelMapper{

    fun entityToProductModel(entity: ProductEntity): Product{
        return Product(entity.id,entity.barCode,entity.name,entity.thumbnailUrl,entity.peremptionDate)

    }

    fun jsonToProductModel(productModelJson: ProductModelJson): Product{
        return Product(null,productModelJson.barCode,productModelJson.product!!.name,productModelJson.product.imageUrl, -1L)
    }
}