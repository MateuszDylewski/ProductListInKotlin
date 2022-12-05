package com.example.productlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ProductViewModel(app: Application): AndroidViewModel(app) {

    private val repo: ProductRepository
    val productList: LiveData<List<Product>>

    init {
        val productDAO = ProductDB.getDatabase(app.applicationContext)!!.getProductDao()
        repo = ProductRepository(productDAO)
        productList = repo.productList
    }

    fun insert(product: Product) {
        repo.insert(product)
    }

    fun update(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.update(product)
        }
    }

    fun delete(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.delete(product)
        }
    }

    fun getLastAddedProduct(): Product {
        return repo.getLastAddedProduct()
    }
}