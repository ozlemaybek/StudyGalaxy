package com.ozlem.studygalaxy.view

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint.Style
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.FragmentEveryweekGoalBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        /*
        // Date Picker Dialog Try
        // Hem soldaki hem de sağdaki için java util seçeneğini seçtik:
        val myCalendar = Calendar.getInstance()

        // dataPicker android widget seçtim listener için function olanı seçtim:
        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateStart(myCalendar, binding.startDateId)
            updateDateEnd(myCalendar, binding.endDateId)
        }*/

        //Burası sorun çıkarabilir!!!
        binding.startDateId.setOnClickListener {

            binding.startDateId.hideKeyboard()

            val myCalendar = Calendar.getInstance()
            val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateStart(myCalendar, binding.startDateId)
            }

            binding.startDateId.hideKeyboard()
            context?.let{
                DatePickerDialog(it, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                    Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
            }!!.show()
        }

        binding.endDateId.setOnClickListener {
            binding.endDateId.hideKeyboard()

            val myCalendar = Calendar.getInstance()
            val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateEnd(myCalendar, binding.endDateId)
            }

            binding.endDateId.hideKeyboard()
            context?.let{
                DatePickerDialog(it, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                    Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
            }!!.show()
        }

        // Date Picker Dialog Try End

        // CHECKBOX
       /* if(binding.checkBoxSelectAllID.isChecked){
            binding.checkBoxFridayId.isChecked = true

        }*/

    }

    // tarih seçmek için tıklandığında klavyeyi kapatmak için:
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun updateDateStart(myCalendar: Calendar, view: View){
        val dateFormat = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        binding.startDateId.setText(simpleDateFormat.format(myCalendar.time))
    }

    private fun updateDateEnd(myCalendar: Calendar, view: View){
        val dateFormat = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        binding.endDateId.setText(simpleDateFormat.format(myCalendar.time))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}