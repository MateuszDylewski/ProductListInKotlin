package com.example.productlist

class ProductRepository(private val productDAO: ProductDAO) {

    val productList = productDAO.getProducts()

    fun insert(product: Product) = productDAO.insert(product)
    fun update(product: Product) = productDAO.update(product)
    fun delete(product: Product) = productDAO.delete(product)
    fun getLastAddedProduct(): Product = productDAO.getLastAddedProduct()

}