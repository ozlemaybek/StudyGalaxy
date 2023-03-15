package com.ozlem.studygalaxy.view

import android.content.ContentValues.TAG
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
import com.ozlem.studygalaxy.databinding.ActivityDeleteAccountBinding

class DeleteAccountActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding : ActivityDeleteAccountBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivityDeleteAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

        // Güncel kullanıcı (uygulamaya şuanda girişyapmış olan kullanıcı)'yı alalım:
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            binding.usernameTitleId.text = user.displayName
            binding.usernameId.text = user.displayName
            binding.emailId.setText(user.email)
            // Firebase şifreye erişim vermiyor (kullanıcı gizliliği) bu yüzden bu şekilde göstereceğiz:
            //binding.passwordId.setText("******")
        } else {
            // No user is signed in
        }
    }

    fun onClickDeleteButton(view : View){

        val user = Firebase.auth.currentUser!!

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        val credential = EmailAuthProvider
            .getCredential(binding.emailId.toString(), binding.passwordId.toString())

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
            .addOnCompleteListener {
                // Kullanıcı şu anda kimlik bilgilerini doğruladı

                Log.d(TAG, "User re-authenticated.")
                // Hesap silme işlemine başlayalım:
                Toast.makeText(this, "Kimlik doğrulama başarılı", Toast.LENGTH_LONG).show()
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User account deleted.")
                            Toast.makeText(this, "Account deleted!", Toast.LENGTH_LONG).show()
                            // Hesap silindikten sonra dign in ekranına geçiş yapalım:
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "Hesap silme başarısız!", Toast.LENGTH_LONG).show()
                        }
                    }
            }


    }

}