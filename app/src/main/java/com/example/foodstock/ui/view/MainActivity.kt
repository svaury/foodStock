package com.example.foodstock.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodstock.R
import com.example.foodstock.model.Product
import com.example.foodstock.ui.adapter.CustomItemTouchHelperCallback
import com.example.foodstock.ui.adapter.ProductListAdapter
import com.example.foodstock.ui.viewmodel.ProductStockViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() , AddProductListener {

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
       // productListAdapter.stateRestorationPolicy= RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        recycleVewProducts.layoutManager = LinearLayoutManager(this)
        recycleVewProducts.adapter = productListAdapter
        val ith = ItemTouchHelper(CustomItemTouchHelperCallback(productListAdapter))
        ith.attachToRecyclerView(recycleVewProducts)
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

    override fun addProduct(product: Product) {
        if(productListAdapter.products.isEmpty()){
            emptyListTv.visibility = View.GONE
            recycleVewProducts.visibility = View.VISIBLE
        }
        if(productListAdapter.products.contains((product))){
            productListAdapter.updateProduct(product)

        }else {
            productListAdapter.addProduct(product)

        }
    }
}