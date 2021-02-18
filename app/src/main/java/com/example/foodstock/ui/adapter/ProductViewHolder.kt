package com.example.foodstock.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodstock.R
import com.example.foodstock.model.Product
import java.util.*

class ProductViewHolder(view: View, val context : Context) : RecyclerView.ViewHolder(view) {


    var tvProductName: TextView = view.findViewById(R.id.tvProductName)
    var ivProduct : ImageView = view.findViewById(R.id.productImageView)
    var tvPeremption :TextView = view.findViewById(R.id.tvPeremption)


    fun bind(product: Product){
        tvProductName.text = product.name

        product.imageUrl?.let {
            Glide.with(context).load(product.imageUrl).into(ivProduct);
        }

        val peremptionDate = Calendar.getInstance()
        peremptionDate.timeInMillis = product.peremptionDate

        tvPeremption.text = "Périme le "+ peremptionDate.get(Calendar.DAY_OF_MONTH) + "/"+ (peremptionDate.get(Calendar.MONTH) +1) + "/"+ peremptionDate.get(Calendar.YEAR)
    }


}