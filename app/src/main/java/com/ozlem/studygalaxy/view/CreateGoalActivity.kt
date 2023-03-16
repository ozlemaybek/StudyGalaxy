package com.ozlem.studygalaxy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.ActivityCreateGoalBinding

class CreateGoalActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding: ActivityCreateGoalBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivityCreateGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

    }

    fun onClickTodayButton(view : View){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val todayGoalFragment = TodayGoalFragment()
        fragmentTransaction.replace(R.id.frameLayoutTimeID , todayGoalFragment).commit()
    }

    fun onClickEveryweekButton(view : View){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val everyweekGoalFragment = EveryweekGoalFragment()
        fragmentTransaction.replace(R.id.frameLayoutTimeID , everyweekGoalFragment).commit()
    }

}