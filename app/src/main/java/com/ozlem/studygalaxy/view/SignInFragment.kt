package com.ozlem.studygalaxy.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    var username : String? = null


    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    // view binding için:
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding için:
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

        binding.signInButtonId.setOnClickListener{
            // Kullanıcının girdiği username'i bir string olarak alalım:
            val username = binding.plainTextUsernameId.text.toString()
            val password = binding.plainTextPasswordId.text.toString()

            if(username != "" && password != ""){
                // Kayıtlı birkullanıcının sign in olması:
                auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //Kullanıcı şu anda girdi. (current user: güncel kullanıcı)
                        val currentUser = auth.currentUser?.displayName.toString()
                        Toast.makeText(context.applicationContext,"Welcome ${currentUser}", Toast.LENGTH_LONG).show()

                        // sign in işlemi başarılı ise diğer ekrana geçelim:
                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener{ exception ->
                    Toast.makeText(context.applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    fun signInOnclick(view: View){
        // Bu metod algılanamıyor!!!
        // Kullanıcının girdiği username'i bir string olarak alalım:
        val username = binding.plainTextUsernameId.text.toString()
        val password = binding.plainTextPasswordId.text.toString()
    }
     */



}