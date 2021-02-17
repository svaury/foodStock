package com.example.foodstock.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodstock.Data
import com.example.foodstock.model.Product
import com.example.foodstock.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddProductViewModel(val productRepository: ProductRepository) : ViewModel() {


    val searchResultLiveData = MutableLiveData<Data<Product>>()

    fun searchProduct(barCode: String, peremptionDate: String) {

        val barCodeError = checkBarCode(barCode)
        val peremptionDateError =checkPeremptionDate(peremptionDate);
        var error = false;

        if(!barCode.isNullOrEmpty()){
            error = true
            searchResultLiveData.postValue(Data.errorBarCode(null,barCodeError))

        }
        if(!peremptionDate.isNullOrEmpty()){
            error = true
            searchResultLiveData.postValue(Data.errorPeremption(null,peremptionDateError))

        }
        if(!error){
            GlobalScope.launch(viewModelScope.coroutineContext + Dispatchers.IO){
                val product = productRepository.getProductByBarCode(barCode)?.first() ?: null
                val peremptionToMillis = convertDateToMillis(peremptionDate)
                postResult(product, peremptionToMillis)

            }
        }

    }

    fun postResult(product: Product ?, peremptionToMillis: Long){
        product?.let {
            if(it.peremptionDate == -1L || it.peremptionDate < peremptionToMillis) {

                val addedProduct = it.peremptionDate != -1L
                it.peremptionDate = peremptionToMillis;
                productRepository.addOrUpdateProduct(it)
                if(addedProduct){
                    searchResultLiveData.postValue(Data.success(product,"Product "+ it.name + " have been added"))
                }else{
                    searchResultLiveData.postValue(Data.success(product,"Product " + it.name + "  have been updated"))

                }

            }else{
                searchResultLiveData.postValue(Data.errorPeremption(null, "Peremption date is highter than peremption of current product"))
            }
        }

    }



    fun convertDateToMillis(peremptionDate: String): Long{
        return 0L
    }


    fun checkBarCode( barCode:String ): String{
        return when {
            barCode.isNullOrEmpty() -> {
                "BarCode must be informed"
            }
            barCode.length > 13 -> {
                "BarCode must contain less than 13 characters"
            }
            else -> {
                "";
            }
        }

    }

    fun checkPeremptionDate( peremptionDate:String ): String{
        return when {
            peremptionDate.isNullOrEmpty() -> {
                "Premption Date must be informed"
            }
            else -> {
                "";
            }
        }

    }
}