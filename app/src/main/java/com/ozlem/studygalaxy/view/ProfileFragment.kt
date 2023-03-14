package com.ozlem.studygalaxy.view

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
                        val intent = Intent(getActivity(), SignInActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.delete_option_id -> {
                        Toast.makeText(getActivity(), "Goal deleted!", Toast.LENGTH_LONG).show()
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