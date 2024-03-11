package com.example.productsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.productsapp.databinding.ActivityMainBinding
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Fresco.initialize(this)
    }
}