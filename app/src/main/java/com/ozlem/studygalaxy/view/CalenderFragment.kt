package com.ozlem.studygalaxy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.google.firebase.Timestamp
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.databinding.FragmentCalenderBinding


class CalenderFragment : Fragment() {

    // view binding için:
    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding için:
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calenderViewId.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->

            val Date = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

            // set this date in TextView for Display

            val strList = Timestamp.now().toDate().toString().split(" ")
            val dateValue = strList[2] + " " + strList[0] + " " + strList[1]
            // binding.todayDateId.setText(dateValue)

            binding.currentDateId.setText(dateValue)
        })

    }

}