package com.ozlem.studygalaxy.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding : ActivityEditProfileBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

        // Güncel kullanıcı (uygulamaya şuanda girişyapmış olan kullanıcı)'yı alalım:
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            binding.usernameTitleId.text = user.displayName
            binding.usernameId.setText(user.displayName)
            binding.emailId.setText(user.email)
            // Firebase şifreye erişim vermiyor (kullanıcı gizliliği) bu yüzden bu şekilde göstereceğiz:
            //binding.passwordId.setText("******") // bunu hint olarak verdim
        } else {
            // No user is signed in
        }
    }

    fun onClickSaveButton(view : View){

        val user = Firebase.auth.currentUser

        if (user != null) {
            if (user.displayName != binding.usernameId.toString()) {
                updateUsername()
            }
            if (user.email != binding.emailId.toString()) {
                updateEmail()
            }

            // Profili güncelledikten sonra signout yapalım ve sign in ekranına geri dönelim:
            auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent) // 2.ekrana geçişi başlat
            finish() // bulunduğum aktiviteyi kapatıp hafızadan sil.

        }else {
            // No user is signed in
            Toast.makeText(this, "Profile is null!", Toast.LENGTH_LONG).show()
        }

        }

    private fun updateUsername(){
        val user = Firebase.auth.currentUser
        if(user!=null){
            // User is signed in
            //username bölümü:

            // Şimdi güncel kullanıcı ile ilgili yapılacak request'i oluşturalım:
            // userProfileChangeRequest: firebase'de hazır tanımlı bir fonksiyon.
            val profileUpdates = userProfileChangeRequest {
                displayName = binding.usernameId.text.toString()
                // photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
            }

            // Şimdi yukarıda oluşturduğumuz profil güncelleme isteğini alıp aşağıda kullanabiliriz.
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile updated.")
                        Toast.makeText(this, "username updated!", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

    private fun updateEmail(){
        val user = Firebase.auth.currentUser
        if(user!=null){
            // email bölümü:
            val newEmail = binding.emailId.text.toString()
            user!!.updateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User email address updated.")
                        Toast.makeText(this, "email updated!", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

}