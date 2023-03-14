package com.ozlem.studygalaxy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ozlem.studygalaxy.*
import com.ozlem.studygalaxy.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    // view binding için :
    private lateinit var binding2 : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view binding için :
        binding2 = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding2.root)

        // İlk açıldığında home page'i göstermek için:
        replaceFragment(HomeFragment())

        // bottom navigation bar'ın listener'ını yazalım:
        binding2.bottomNavigationViewId.setOnItemSelectedListener {

            when (it.itemId) {

                // Bottom Navigation Bar'da hangi icona tıkladığımızda hangi fragment'a geçeceğimizin kodları:
                // Burada sol tarafta bottom_navigation_menu.xml'deki id'leri kullanıyoruz,
                // Sağ tarafta ise Fragment'ların dosya isimlerini kullanıyoruz:
                R.id.statistics_icon_id -> replaceFragment(StatisticsFragment())
                R.id.goals_icon_id -> replaceFragment(GoalsFragment())
                R.id.home_icon_id -> replaceFragment(HomeFragment())
                R.id.calender_icon_id -> replaceFragment(CalenderFragment())
                R.id.profile_icon_id -> replaceFragment(ProfileFragment())

                else -> {}

            }
            true
        }
        // ekleme



        // ekleme

    }




        private fun replaceFragment(fragment: Fragment) {

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            // activity_main2.xml'deki frame layout'un id'si: frameLayout2ID :
            fragmentTransaction.replace(R.id.frameLayout2ID, fragment)
            fragmentTransaction.commit()

        }
}