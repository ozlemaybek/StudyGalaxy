package com.ozlem.studygalaxy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding: ActivitySignUpBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth
    }

    fun onClickSignInText(view : View){

        // sign up ekranına geçilsin:
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun signUpOnclick(view: View){

        // Sign up bilgilerini alalım:
        // trim(): aradaki boşlukları silmesi için
        val username = binding.plainTextUsernameId.text.toString()
        val email = binding.plainTextEmailId.text.toString().trim()
        val password = binding.plainTextPasswordId.text.toString()


        // Kullanıcımızı oluşturalım:
        // Kullanıcıyı oluşturduğumuz satırın hemen devamında bir listener var ve ne olacağını kontrol ediyor.
        // onCompleteListener'ın değişken adı "task" olarak belirlenmiş. Başka bir şeyde kullanabilirdik.
        // task : authentication'ın sonucu
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // if içnde isSuccessful ile task başarılımı bunu kontrol ediyoruz.
                Toast.makeText(applicationContext, "Sign Up was successful.", Toast.LENGTH_LONG).show()

                // USERNAME'İ GÜNCELLEMEK:
                // Önce güncel kullanıcıyı alalım:
                val currentUser = auth.currentUser
                // Şimdi güncel kullanıcı ile ilgili yapılacak request'i oluşturalım:
                // userProfileChangeRequest: firebase'de hazır tanımlı bir fonksiyon.
                val profileUpdatesRequest = userProfileChangeRequest {
                    displayName = username
                }

                // Şimdi yukarıda oluşturduğumuz profil güncelleme isteğini alıp aşağıda kullanabiliriz.
                if(currentUser != null){
                    currentUser.updateProfile(profileUpdatesRequest).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext, "Profile username added.", Toast.LENGTH_LONG).show()
                        }

                    }

                    // Burada ilk kez kayıt olan bir kullanıcı içindefault bir hedef oluşturacağız.


                    // Kayıt olma işlemi başarılı ise kullanıcı MainActivity2 ekranına aktarılacak:
                    // 1.parametre: context(bulunduğum yer)
                    // 2.parametre: gideceğim yer
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent) // 2.ekrana geçişi başlat
                    finish() // bulunduğum aktiviteyi kapatıp hafızadan sil.
                }
            }
        }.addOnFailureListener{ exception ->
            // addOnFailureListener bize bir exception yani hata veriyor.
            // Aldığımız exception'ı gösterelim:
            // 1.parametre: context
            // 2.parametre: gösterilecek mesaj metni
            // 3.parametre: duration
            // exception.localizedMessage kısmı ile hata mesajlarının kullanıcının anlayacağı bir dil ile görünmesini sağladık.
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }

    }
}