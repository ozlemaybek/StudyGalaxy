package com.ozlem.studygalaxy.view

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
import com.ozlem.studygalaxy.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    // view binding için:
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding için:
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        // DENEME:
        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth



        // DENEME BİTTİ

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun signUpOnclick(view: View){
        // Kullanıcının girdiği username'i bir string olarak alalım:
        val username = binding.plainTextUsernameId.text.toString()
        val email = binding.plainTextEmailId.text.toString()
        val password = binding.plainTextPasswordId.text.toString()

    }


}