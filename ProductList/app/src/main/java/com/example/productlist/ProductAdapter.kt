package com.example.productlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.productlist.databinding.ElementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductAdapter(private val viewModel: ProductViewModel) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private var productList = emptyList<Product>()

    class ViewHolder(val binding: ElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ElementBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productNameTextField.text = productList[position].name
        holder.binding.ifBoughtCheckBox.isChecked = productList[position].ifBought

        holder.binding.root.setOnClickListener {
            deleteProduct(productList[position])
        }
        holder.binding.ifBoughtCheckBox.setOnClickListener {
                updateProduct(
                    Product(
                        productList[position].id,
                        productList[position].name,
                        !productList[position].ifBought
                    )
                )
        }
        holder.binding.editButton.setOnClickListener {
            val intent = Intent(holder.binding.root.context, AddProduct::class.java)
            intent.putExtra("productId", productList[position].id)
            intent.putExtra("productName", productList[position].name)
            intent.putExtra("productIfBought", productList[position].ifBought)
            startActivity(
                holder.binding.root.context,
                intent,
                null
            )
        }
    }

    override fun getItemCount(): Int = productList.size

    fun setProductList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    fun addProduct(product: Product) {
        viewModel.insert(product)
        notifyDataSetChanged()
    }

    fun deleteProduct(product: Product) {
        viewModel.delete(product)
        notifyDataSetChanged()
    }

    fun updateProduct(product: Product) {
        viewModel.update(product)
        notifyDataSetChanged()
    }

    fun getLastAddedProduct(): Product {
        return viewModel.getLastAddedProduct()
    }
}