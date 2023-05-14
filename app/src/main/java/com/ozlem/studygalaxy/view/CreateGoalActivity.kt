package com.ozlem.studygalaxy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
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

        // time pick:
        binding.dailyGoalTimeId1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                //binding.dailyGoalTimeId1.setText("Text in EditText : "+s)

                    try{
                        if (binding.dailyGoalTimeId1.getText().toString().toInt() > 23) {
                            binding.dailyGoalTimeId1.setText("23")
                        }
                    }catch (e : NumberFormatException){
                        // hint'i gösteriyor ama silip istediğimizi yapabiliyoruz.
                        //binding.dailyGoalTimeId1.setText("01")
                    }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.dailyGoalTimeId2.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                //binding.dailyGoalTimeId1.setText("Text in EditText : "+s)

                try{
                    if (binding.dailyGoalTimeId2.getText().toString().toInt() > 59) {
                        binding.dailyGoalTimeId2.setText("59")
                    }
                }catch (e : NumberFormatException){
                    // hint'i gösteriyor ama silip istediğimizi yapabiliyoruz.
                    //binding.dailyGoalTimeId1.setText("01")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })



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