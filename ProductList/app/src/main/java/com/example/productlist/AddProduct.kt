package com.example.productlist

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.productlist.databinding.ActivityAddProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class AddProduct : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var productViewModel = ProductViewModel(application)
        var productAdapter = ProductAdapter(productViewModel)
        //TODO handle edit product view when product is passed
        var productName = intent.getStringExtra("productName").toString()
        var productId = intent.getLongExtra("productId", -1)
        var productIfBought = intent.getBooleanExtra("productIfBought", false)
        val isEditing: Boolean = (productName != "" && productId != -1L)
        if (isEditing) {
            binding.nameInput.setText(productName)
        }

        binding.saveButton.setOnClickListener {
            if (binding.nameInput.text.toString() == "") {
                Toast.makeText(
                    binding.root.context,
                    "Name cannot be empty!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (isEditing) {
                    CoroutineScope(Dispatchers.IO).launch {
                        productAdapter.updateProduct(
                            Product(
                                id = productId,
                                name = binding.nameInput.text.toString(),
                                ifBought = productIfBought
                            )
                        )
                    }
                } else {
                    var product = Product(
                        name = binding.nameInput.text.toString(),
                        ifBought = false
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        productAdapter.addProduct(product)
                        val newProduct = productAdapter.getLastAddedProduct()
                        sendBroadcast(Intent().also {
                            it.component = ComponentName(
                                "com.example.myapplication",
                                "com.example.myapplication.MyReceiver"
                            )
                            it.putExtra("productId", newProduct.id)
                            it.putExtra("productName", newProduct.name)
                            it.putExtra("productIfBought", newProduct.ifBought)
                        })
                    }

                }
                finish()
            }
        }

        binding.cancleButton.setOnClickListener {
            finish()
        }
    }
}