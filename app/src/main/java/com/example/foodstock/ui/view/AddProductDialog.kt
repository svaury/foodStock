package com.example.foodstock.ui.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.foodstock.R
import com.example.foodstock.utils.Status
import com.example.foodstock.ui.viewmodel.AddProductViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.product_add_dialog_layout.*
import org.koin.android.viewmodel.ext.android.viewModel


class AddProductDialog() : DialogFragment() {

    val addProductViewModel : AddProductViewModel by viewModel<AddProductViewModel>()

    lateinit var addProductListener: AddProductListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       addProductListener = (activity as MainActivity)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("CreateView","Createview")
        return  inflater.inflate(R.layout.product_add_dialog_layout, null);

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDialog()
        setUpLiveData()

    }



    fun initDialog(){

        searchValidateButton.setOnClickListener {
            progressBar.visibility =View.VISIBLE
            peremptionDateInputLayout.error = null
            barCodeInputLayout.error = null

            addProductViewModel.searchProduct(barCodeEditText.text.toString(),peremptionDateEditText.text.toString())
        }
         cancelButton.setOnClickListener {
             dismiss()
         }
       dialog?.apply {
           setCancelable(false)
           setCanceledOnTouchOutside(false)
       }


    }

    fun setUpLiveData(){


        addProductViewModel.searchResultLiveData.observe(this, Observer {
            progressBar.visibility =View.GONE

            when(it.status){
                Status.ERROR -> {
                    Toast.makeText(activity,it.message , Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(activity,it.message , Toast.LENGTH_LONG).show()
                    addProductListener.addProduct(it.data!!)
                    dismiss()

                }
                Status.ERROR_BARCODE ->{
                    Toast.makeText(activity,it.message , Toast.LENGTH_LONG).show()
                    barCodeInputLayout.error = it.message
                }
                Status.ERROR_PEREMPTION ->{
                    Toast.makeText(activity,it.message , Toast.LENGTH_LONG).show()
                    peremptionDateInputLayout.error = it.message
                }
            }


        })

    }

    companion object {
        fun newInstance(title: String?): AddProductDialog {
            val frag = AddProductDialog()
            val args = Bundle()
            args.putString("addProduct", title)
            frag.arguments = args
            return frag
        }
    }
}