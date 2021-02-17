package com.example.foodstock.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodstock.R
import com.example.foodstock.model.Product
import java.util.*

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    var tvProductName: TextView = view.findViewById(R.id.tvProductName)
    var ivProduct : ImageView = view.findViewById(R.id.productImageView)
    var tvPeremption :TextView = view.findViewById(R.id.tvPeremption)


    fun bind(product: Product){
        tvProductName.text = product.name

        product.imageUrl?.let {

        }

        val peremptionDate = Calendar.getInstance()
        peremptionDate.timeInMillis = product.peremptionDate

        tvPeremption.text = "Périme le "+ peremptionDate.get(Calendar.DAY_OF_MONTH) + "/"+ (peremptionDate.get(Calendar.MONTH) +1) + "/"+ peremptionDate.get(Calendar.YEAR)
    }


}