package com.example.foodstock.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodstock.MyApplication
import com.example.foodstock.R
import com.example.foodstock.model.Product
import com.example.foodstock.ui.viewmodel.AddProductViewModel
import com.example.foodstock.ui.viewmodel.ProductStockViewModel
import java.util.*
import kotlin.collections.ArrayList


class ProductListAdapter(var products:ArrayList<Product>, val context : Context, val productViewModel: ProductStockViewModel): RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.product_list_detail_layout,p0,false)
        return ProductViewHolder(view, context)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.bind(products[position])
    }

    fun addProduct(product : Product){
        products.add(product)
        notifyDataSetChanged()
    }


    fun updateProduct(product: Product){
        val index = products.indexOf(product)
        products[index].peremptionDate = product.peremptionDate
        notifyDataSetChanged()
    }

    fun addProducts(products : List<Product>){
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun switchProductsPosition(currentPos: kotlin.Int, targetPos : kotlin.Int){
        Collections.swap(products, currentPos, targetPos)
        notifyItemMoved(currentPos, targetPos)
    }

    fun removeProduct(position: Int){
        val productRemove =  products.removeAt(position)
        productViewModel.removeProducts(productRemove)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {

        return products.size
    }


}

