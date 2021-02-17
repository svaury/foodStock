package com.example.foodstock.ui.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.foodstock.R
import com.example.foodstock.ui.viewmodel.AddProductViewModel
import kotlinx.android.synthetic.main.product_add_dialog_layout.*
import org.koin.android.viewmodel.ext.android.viewModel


class AddProductDialog : DialogFragment() {
    private var mEditText: EditText? = null

    val addProductViewModel by viewModel<AddProductViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.product_add_dialog_layout, container)
    }


    fun setUpLiveData(){

        addProductViewModel.searchResultLiveData.observe(this, Observer {
            Log.i("AddProductDialog","Message "+ it.message )
        })

    }
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLiveData()


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Research a product")
            .setMessage("Please select a barcode and a peremption date")
            .setPositiveButton("Validate"
            ) { dialog, which ->
                addProductViewModel.searchProduct(barCodeEditText.text.toString(),peremptionDateEditText.text.toString())
            }

        alertDialogBuilder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which ->
                if (dialog != null ) {
                    dialog.dismiss()
                }
            })



        return alertDialogBuilder.create()
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