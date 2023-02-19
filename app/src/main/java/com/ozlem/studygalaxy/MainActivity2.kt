package com.ozlem.studygalaxy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozlem.studygalaxy.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    // view binding için :
    private lateinit var binding2 : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding2.root)
    }
}