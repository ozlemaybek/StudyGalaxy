package com.ozlem.studygalaxy.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    // view binding için:
    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Güncel kullanıcı (uygulamaya şuanda giriş yapmış olan kullanıcı)'yı alalım:
        val user = Firebase.auth.currentUser
        if(user != null){
            //Toast.makeText(getActivity(), "Kullanıcı id: +${user!!.uid} + username: ${user!!.displayName}", Toast.LENGTH_LONG).show()
            // User is signed in
            // İlgili alanlara bilgileri bastıralım:
            binding.usernameId.text = user.displayName
        }
        else{
            // No user is signed in
            Toast.makeText(getActivity(), "user null döndü", Toast.LENGTH_LONG).show()
        }


        // FAB Button Listener:
        binding.floatingActionButtonID.setOnClickListener {
            // Respond to FAB click
            val intent = Intent(getActivity(), CreateGoalActivity::class.java)
            startActivity(intent)
            /*
            val popupMenu = PopupMenu(getActivity(), binding.floatingActionButtonID)
            popupMenu.menuInflater.inflate(R.menu.floatin_action_button_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.add_goal_option_id -> {
                        val intent = Intent(getActivity(), SignInActivity::class.java)
                        startActivity(intent)
                        true
                    }
                }
                true
            }
            popupMenu.show()*/
        }

        // onViewCreated burada bitiyor
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}