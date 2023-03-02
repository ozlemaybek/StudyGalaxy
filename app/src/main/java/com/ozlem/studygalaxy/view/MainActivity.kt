package com.ozlem.studygalaxy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.ActivityMain2Binding
import com.ozlem.studygalaxy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding : ActivityMainBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

    }

    fun onClickSignInText(view : View){

        // Fragment'ı kullanabilmek için FragmentManager denen yapıyı çağırmalıyız:
        val fragmentManager = supportFragmentManager

        // Yapacağımız işlemleri başlatmak için fragmentTransaction denen bir yapı oluşturuyoruz.
        // Bu fragment ile ilgili değişiklik yapacağız demiş oluyoruz:
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Artık işlemlerimizi fragmentTransaction kullanarak yapabiliriz.

        // Fragment oluşturalım:
        // Eşitliğin sağ tarafı Fragment'ın ismi:
        val signInFragment = SignInFragment()

        // replace: Öncesinde bir fragment varsa onu kaldırır ve istediğimiz fragment'ı ekler.
        // ilk parametre: Bu fragment'ı kim kullanacak
        // ikinci fragment: hangi fragment'ı gösterecek
        // .commit() diyerek transaction'ı commit ediyorum yani burada yaptığım işlemi artık sonlandırıyorum:
        fragmentTransaction.replace(R.id.frameLayoutID, signInFragment).commit()
    }

    fun onClickSignUpText(view : View){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Fragment oluşturalım:
        // Eşitliğin sağ tarafı Fragment'ın ismi:
        val signUpFragment = SignUpFragment()

        // replace: Öncesinde bir fragment varsa onu kaldırır ve istediğimiz fragment'ı ekler.
        // ilk parametre: Bu fragment'ı kim kullanacak
        // ikinci fragment: hangi fragment'ı gösterecek
        // .commit() diyerek transaction'ı commit ediyorum yani burada yaptığım işlemi artık sonlandırıyorum:
        fragmentTransaction.replace(R.id.frameLayoutID, signUpFragment).commit()

    }

    fun signInOnclick(view : View){

            // Kullanıcının girdiği username'i bir string olarak alalım:
            val username = binding.plainTextUsernameId.text.toString()
            val password = binding.plainTextPasswordId.text.toString()

            if(username != "" && password != ""){
                // Kayıtlı birkullanıcının sign in olması:
                auth.signInWithEmailAndPassword(username, password) .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //Kullanıcı şu anda girdi. (current user: güncel kullanıcı)
                        val currentUser = auth.currentUser?.displayName.toString()
                        Toast.makeText(applicationContext,"Welcome ${currentUser}", Toast.LENGTH_LONG).show()

                        // sign in işlemi başarılı ise diğer ekrana geçelim:
                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener{ exception ->
                    Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }

        // sign in işlemi başarılı ise diğer ekrana geçelim:
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
        finish()
    }

}