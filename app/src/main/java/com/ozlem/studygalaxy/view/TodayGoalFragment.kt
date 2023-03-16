package com.ozlem.studygalaxy.view

import android.app.DatePickerDialog
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
import com.ozlem.studygalaxy.databinding.FragmentTodayGoalBinding
import java.text.SimpleDateFormat
import java.util.*

class TodayGoalFragment : Fragment() {

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    // view binding için:
    private var _binding: FragmentTodayGoalBinding? = null
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
        _binding = FragmentTodayGoalBinding.inflate(inflater, container, false)
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

        // Date Picker Dialog Try
        // Hem soldaki hem de sağdaki için java util seçeneğini seçtik:
        val myCalendar = Calendar.getInstance()

        // dataPicker android widget seçtim listener için function olanı seçtim:
        val datePicker = DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate(myCalendar)
        }

        //Burası sorun çıkarabilir!!!
                binding.todayDateId.setOnClickListener {
                    context?.let{DatePickerDialog(it, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))}!!.show()
                }

        // Date Picker Dialog Try End
    }

    private fun updateDate(myCalendar: Calendar){
        val dateFormat = "dd-mm-yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        binding.todayDateId.setText(simpleDateFormat.format(myCalendar.time))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}