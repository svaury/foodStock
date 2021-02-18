package com.example.foodstock.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.foodstock.R
import com.example.foodstock.ui.adapter.ProductListAdapter
import com.example.foodstock.ui.viewmodel.ProductStockViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var productListAdapter : ProductListAdapter

    val productStockViewModel : ProductStockViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()
        setUpLiveData()


    }

    fun setupUi(){

        productListAdapter = ProductListAdapter(ArrayList(),this)
        recycleVewProducts.adapter = productListAdapter
        buttonAddProduct.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val fm: FragmentManager = supportFragmentManager
        val alertDialog: AddProductDialog = AddProductDialog.newInstance("addProduct")
        alertDialog.show(fm, "addProduct")
    }
    fun setUpLiveData(){
        productStockViewModel.productListLiveData.observe(this, Observer {
            if(it.isNullOrEmpty()){
                emptyListTv.visibility = View.VISIBLE
                recycleVewProducts.visibility = View.GONE
            }else{
                emptyListTv.visibility = View.GONE
                recycleVewProducts.visibility = View.VISIBLE
                productListAdapter.addProducts(it)
                productListAdapter.notifyDataSetChanged()
            }
        })


    }
}