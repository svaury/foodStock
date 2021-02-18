package com.example.foodstock.ui.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.foodstock.R
import com.example.foodstock.utils.Status
import com.example.foodstock.ui.viewmodel.AddProductViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel


class AddProductDialog(val addProductListener: AddProductListener) : DialogFragment() {

    val addProductViewModel : AddProductViewModel by viewModel<AddProductViewModel>()
    lateinit var customLayout : View


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpLiveData()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

         customLayout = layoutInflater.inflate(R.layout.product_add_dialog_layout, null);

        val barCodeEditText = customLayout.findViewById<TextInputEditText>(R.id.barCodeEditText)
        val peremptionDateEditText = customLayout.findViewById<TextInputEditText>(R.id.peremptionDateEditText)
        val searchValidateButton = customLayout.findViewById<Button>(R.id.searchValidateButton)

        searchValidateButton.setOnClickListener {
            addProductViewModel.searchProduct(barCodeEditText.text.toString(),peremptionDateEditText.text.toString())
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Research a product")
            .setView(customLayout)
            .setCancelable(false)
            .setMessage("Please select a barcode and a peremption date")
            alertDialogBuilder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which ->
                dialog?.dismiss()
            })

        val alertDialog =  alertDialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)


        return alertDialog
    }


    fun setUpLiveData(){


        addProductViewModel.searchResultLiveData.observe(this, Observer {
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
                    val inputLayout= customLayout?.findViewById<TextInputLayout>(R.id.barCodeInputLayout)
                    inputLayout.error = it.message
                }
                Status.ERROR_PEREMPTION ->{
                    Toast.makeText(activity,it.message , Toast.LENGTH_LONG).show()
                    val inputLayout= customLayout?.findViewById<TextInputLayout>(R.id.peremptionDateInputLayout)
                    inputLayout.error = it.message
                }
            }


        })

    }

    companion object {
        fun newInstance(title: String?, addProductListener: AddProductListener): AddProductDialog {
            val frag = AddProductDialog(addProductListener)
            val args = Bundle()
            args.putString("addProduct", title)
            frag.arguments = args
            return frag
        }
    }
}