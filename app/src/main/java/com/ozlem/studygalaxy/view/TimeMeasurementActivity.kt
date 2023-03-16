package com.ozlem.studygalaxy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.databinding.ActivityTimeMeasurementBinding

class TimeMeasurementActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding: ActivityTimeMeasurementBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivityTimeMeasurementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

    }

}