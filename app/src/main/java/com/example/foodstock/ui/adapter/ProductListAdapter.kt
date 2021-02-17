package com.example.foodstock.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodstock.MyApplication
import com.example.foodstock.R
import com.example.foodstock.model.Product


class ProductListAdapter(var foods:ArrayList<Product>, val context : Context): RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.product_list_detail_layout,p0,false)
        return ProductViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.bind(foods[position])
    }

    fun addProduct(product : Product){
        foods.add(product)
        notifyDataSetChanged()
    }

    fun addProducts(products : List<Product>){
        foods.clear()
        foods.addAll(products)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return foods.size
    }


}

