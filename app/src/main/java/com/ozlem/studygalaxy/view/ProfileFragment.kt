package com.ozlem.studygalaxy.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    // view binding için:
    private var _binding: FragmentProfileBinding? = null
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
            binding.usernameTitleId.text = user.displayName
            binding.usernameId.text = user.displayName
            binding.emailId.text = user.email
            // Firebase şifreye erişim vermiyor (kullanıcı gizliliği) bu yüzden bu şekilde göstereceğiz:
            binding.passwordId.text = "******"
        }
        else{
            // No user is signed in
            Toast.makeText(getActivity(), "user null döndü", Toast.LENGTH_LONG).show()
        }

        // Uygulamadan çıkış:
        binding.logOutButtonId.setOnClickListener {
            auth.signOut()
            activity?.let {
                val intent = Intent(it, SignInActivity::class.java)
                it.startActivity(intent)
            }
        }

        //Herhangi bir imageView ile popup menu
        binding.settingsButtonID.setOnClickListener {
            val popupMenu = PopupMenu(getActivity(),binding.settingsButtonID)
            popupMenu.menuInflater.inflate(R.menu.profile_settings_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{ item ->
                when(item.itemId) {
                    R.id.edit_option_id -> {
                        val intent = Intent(getActivity(), EditProfileActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.change_password_option_id -> {
                        val intent = Intent(getActivity(), ChangePasswordActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.delete_option_id -> {
                        val intent = Intent(getActivity(), DeleteAccountActivity::class.java)
                        startActivity(intent)
                        //Toast.makeText(getActivity(), "Goal deleted!", Toast.LENGTH_LONG).show()
                        true
                    }
                }
                true
            }
            popupMenu.show()
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}