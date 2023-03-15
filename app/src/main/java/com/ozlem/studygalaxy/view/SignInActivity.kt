package com.ozlem.studygalaxy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding: ActivitySignInBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

        // Giriş yapmış kullanıcıyı hatırlamak:
        // currentUser bize nullable olarak veriliyor yani oladabilir, olmayadabilir.
        // Eğer null ise yok demektir yani firebase kullanıcının giriş yaptığını anlamamış demektir.
        val currentUser = auth.currentUser

        // Kullanıcı daha önce uygulamaya giriş yaptıysa ve uygulamayı şuan açarsa log-in ekranı gösterilmeden
        // direkt diğer ekrana geçilecek.

        if(currentUser != null){
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun onClickSignUpText(view : View){

        // sign up ekranına geçilsin:
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun signInOnclick(view : View){

        // Kullanıcının girdiği username'i bir string olarak alalım:
        val email = binding.plainTextEmailId.text.toString()
        val password = binding.plainTextPasswordId.text.toString()

        if(email != "" && password != ""){
            // Kayıtlı birkullanıcının sign in olması:
            auth.signInWithEmailAndPassword(email, password) .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Kullanıcı şu anda girdi. (current user: güncel kullanıcı)
                    val currentUser = auth.currentUser?.displayName.toString()
                    Toast.makeText(applicationContext,"Welcome ${currentUser}", Toast.LENGTH_LONG).show()

                    // sign in işlemi başarılı ise diğer ekrana geçelim:
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Sign in işlemi geçersiz", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{ exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
                exception?.printStackTrace()


            }
        }
    }



}