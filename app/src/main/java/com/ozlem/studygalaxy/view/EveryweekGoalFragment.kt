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
import com.ozlem.studygalaxy.databinding.FragmentEveryweekGoalBinding

class EveryweekGoalFragment : Fragment() {

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    // view binding için:
    private var _binding: FragmentEveryweekGoalBinding? = null
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
        _binding = FragmentEveryweekGoalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Güncel kullanıcı (uygulamaya şuanda giriş yapmış olan kullanıcı)'yı alalım:
        val user = Firebase.auth.currentUser
        if(user != null){
            Toast.makeText(getActivity(), "Kullanıcı id: +${user!!.uid} + username: ${user!!.displayName}", Toast.LENGTH_LONG).show()
            // User is signed in
            // İlgili alanlara bilgileri bastıralım:
            //binding.usernameId.text = user.displayName
        }
        else{
            // No user is signed in
            Toast.makeText(getActivity(), "user null döndü", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}