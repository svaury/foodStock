package com.example.foodstock.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodstock.Data
import com.example.foodstock.model.Product
import com.example.foodstock.repository.ProductRepository
import com.example.foodstock.utils.ConvertDateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddProductViewModel(val productRepository: ProductRepository) : ViewModel() {


    val searchResultLiveData = MutableLiveData<Data<Product>>()

    fun searchProduct(barCode: String, peremptionDate: String) {

        val barCodeError = checkBarCode(barCode)
        val peremptionDateError =checkPeremptionDate(peremptionDate);
        var error = false;

        if(!barCodeError.isNullOrEmpty()){
            error = true
            searchResultLiveData.postValue(Data.errorBarCode(null,barCodeError))

        }
        if(!peremptionDateError.isNullOrEmpty() && !error){
            error = true
            searchResultLiveData.postValue(Data.errorPeremption(null,peremptionDateError))

        }
        if(!error){
            GlobalScope.launch(viewModelScope.coroutineContext + Dispatchers.IO){
                productRepository.getProductByBarCode(barCode)?.collect { product-> postResult(product, ConvertDateUtils.convertDateToMillis(peremptionDate))
                }
            }
        }

    }

    fun postResult(productData: Data<Product> , peremptionToMillis: Long){

        productData.data?.let {
            if(it.peremptionDate == -1L || it.peremptionDate > peremptionToMillis) {

                val addedProduct = it.peremptionDate == -1L
                it.peremptionDate = peremptionToMillis;
                productRepository.addOrUpdateProduct(it)
                if(addedProduct){
                    searchResultLiveData.postValue(Data.success(it,"Product "+ it.name + " have been added"))
                }else{
                    searchResultLiveData.postValue(Data.success(it,"Product " + it.name + "  have been updated"))
                }

            }else{
                searchResultLiveData.postValue(Data.error(null, "Peremption date is highter than peremption of current product"))
            }
        }?:let{
            searchResultLiveData.postValue(Data.error(null, productData.message?:""))

        }



    }


    fun checkDateFormat(peremptionDate: String): Boolean{

        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        formatter.setLenient(false)
        return try {
            val date: Date = formatter.parse(peremptionDate)
            true
        } catch (e: ParseException) {
            false;
        }
    }

    fun checkBarCode( barCode:String? ): String{
        return when {
            barCode.isNullOrEmpty() -> {
                "Barcode must be informed"
            }
            barCode.length != 13 -> {
                "Barcode must contain 13 character. If there is not 13 , complete with 0 before codeBar"
            }
            else -> {

                val barCode = barCode.toLongOrNull()
                barCode?.let{
                    return ""
                }?:let{
                    return "BarCode must contain only number"
                }
            }
        }
    }

    fun checkPeremptionDate( peremptionDate:String ): String{

            // testDate format
         return  if(peremptionDate.isNullOrEmpty()){
                "Premption Date must be informed"
            }
            else if (!checkDateFormat(peremptionDate)){
                "Format must be dd/MM/yyyy"
            }
            else {
                "";
            }

    }
}