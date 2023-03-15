package com.ozlem.studygalaxy.view

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding : ActivityChangePasswordBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

        // Güncel kullanıcı (uygulamaya şuanda girişyapmış olan kullanıcı)'yı alalım:
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            binding.usernameTitleId.text = user.displayName
        } else {
            // No user is signed in
        }

    }

    fun onClickSaveButton(view : View){

        val user = Firebase.auth.currentUser
        updatePassword()

    }

    private fun updatePassword(){
        val user = Firebase.auth.currentUser!!

        if(user != null){
            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
            val credential = EmailAuthProvider
                .getCredential(binding.emailId.toString(), binding.oldPasswordId.toString())

            // Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                .addOnCompleteListener {
                    // Kullanıcı şu anda kimlik bilgilerini doğruladı
                        Log.d(ContentValues.TAG, "User re-authenticated.")

                        //password bölümü:
                        val newPassword = binding.newPasswordId.text.toString()
                        user!!.updatePassword(newPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(ContentValues.TAG, "User password updated.")
                                    Toast.makeText(this, "password updated!", Toast.LENGTH_LONG).show()
                                }
                            }
                        // Profili güncelledikten sonra signout yapalım ve sign in ekranına geri dönelim:
                        auth.signOut()
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent) // 2.ekrana geçişi başlat
                        finish() // bulunduğum aktiviteyi kapatıp hafızadan sil.
                    }.addOnFailureListener {
                    it.printStackTrace()
                    Toast.makeText(this, "email or password is wrong!", Toast.LENGTH_LONG).show()
                }

    }
    }

}