package com.ozlem.studygalaxy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ozlem.studygalaxy.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}