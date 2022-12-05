package com.example.productlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        binding.addButton.setOnClickListener {
            startActivity(Intent(applicationContext, AddProduct::class.java))
        }

        var productViewModel = ProductViewModel(application)
        var productListAdapter = ProductAdapter(productViewModel)
        productViewModel.productList.observe(this, Observer {
            productListAdapter.setProductList(it)
        })

        binding.productList.layoutManager = LinearLayoutManager(this)

        binding.productList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        binding.productList.adapter = productListAdapter

    }
}