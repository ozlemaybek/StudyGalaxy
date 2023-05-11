package com.ozlem.studygalaxy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
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

        // İlk değerini sıfır yapıyoruz çünkü elimizde başlangıçta zaman yok.
        var stopTime : Long = 0

        binding.startButtonId.setOnClickListener {
            // sistem saatini alalım:
            // aşağıdaki satır sayesinde sistem saatini alıp kronometre içerisine aktarabiliyoruz:
            binding.chronometerId.base = SystemClock.elapsedRealtime() + stopTime

            // burada şuna dikkat etmeliyiz kronometreyi başlattık
            // 10 sn saydı durdurduk ve tekrar başlattık 11 12 diye gitmesi lazım
            // durdurduğumuz süreyi kronometrenin ekleyip devam etmesi lazım
            // o zamanda stopTime değişkeninde tutuluyor.

            // şimdi kronometreyi çalıştıralım:
            binding.chronometerId.start()
            // kronometreyi başlattığımız zaman start butonunu pasif kılmalıyız:
            binding.startButtonId.visibility = View.GONE
            // ve pause butonunu aktive etmeliyiz:
            binding.pauseButtonId.visibility = View.VISIBLE
        }

        binding.pauseButtonId.setOnClickListener{
            stopTime = binding.chronometerId.base-SystemClock.elapsedRealtime()
            // şimdi kronometreyi durduralım:
            binding.chronometerId.stop()

            // kronometreyi durdurduğumuz zaman pause butonunu pasif kılmalıyız:
            binding.pauseButtonId.visibility = View.GONE
            // ve start butonunu aktive etmeliyiz:
            binding.startButtonId.visibility = View.VISIBLE
        }

    }

}