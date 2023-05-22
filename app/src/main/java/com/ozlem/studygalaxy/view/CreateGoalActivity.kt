package com.ozlem.studygalaxy.view

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.databinding.ActivityCreateGoalBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class CreateGoalActivity : AppCompatActivity(){

    // view binding için :
    private lateinit var binding: ActivityCreateGoalBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    // Database objemizi oluşturalım:
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivityCreateGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

        // time pick:
        binding.dailyGoalTimeId1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                //binding.dailyGoalTimeId1.setText("Text in EditText : "+s)

                    try{
                        if (binding.dailyGoalTimeId1.getText().toString().toInt() > 23) {
                            binding.dailyGoalTimeId1.setText("23")
                        }
                    }catch (e : NumberFormatException){
                        // hint'i gösteriyor ama silip istediğimizi yapabiliyoruz.
                        //binding.dailyGoalTimeId1.setText("01")
                    }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.dailyGoalTimeId2.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                //binding.dailyGoalTimeId1.setText("Text in EditText : "+s)

                try{
                    if (binding.dailyGoalTimeId2.getText().toString().toInt() > 59) {
                        binding.dailyGoalTimeId2.setText("59")
                    }
                }catch (e : NumberFormatException){
                    // hint'i gösteriyor ama silip istediğimizi yapabiliyoruz.
                    //binding.dailyGoalTimeId1.setText("01")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        // Date Picker Dialog Try
        // Hem soldaki hem de sağdaki için java util seçeneğini seçtik:
       /* val myCalendar = Calendar.getInstance()

        // dataPicker android widget seçtim listener için function olanı seçtim:
        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate(myCalendar)
        }*/

        //Burası sorun çıkarabilir!!!
        binding.todayDateId.setOnClickListener {

            // Date Picker Dialog Try
            // Hem soldaki hem de sağdaki için java util seçeneğini seçtik:
            val myCalendar = Calendar.getInstance()

            // dataPicker android widget seçtim listener için function olanı seçtim:
            val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate(myCalendar)
            }


            binding.todayDateId.hideKeyboard()
            this?.let{
                DatePickerDialog(it, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                    Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
            }!!.show()
        }


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
            this?.let{
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
            this?.let{
                DatePickerDialog(it, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                    Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
            }!!.show()
        }

        // Date Picker Dialog Try End


        // oncreate'deyiz:

        binding.todayButtonId.setOnClickListener {
            binding.constraintLayoutIdToday.visibility = View.VISIBLE
            binding.constraintLayoutIdEveryweek.visibility = View.GONE
        }

        binding.everyweekButtonId.setOnClickListener {
            binding.constraintLayoutIdEveryweek.visibility = View.VISIBLE
            binding.constraintLayoutIdToday.visibility = View.GONE
        }

    }

    // Bu fonksiyon bir hedef oluşturmak için:
    fun onClickSaveButton(view: View){

        var dateRange : String

        if (binding.constraintLayoutIdToday.visibility == View.VISIBLE){

            // Hedef için random bir id oluşturalım:
            val uuid = UUID.randomUUID()
            // Hedef başlığını alalım:
            val goalTitle = binding.goalTitleId.text.toString()
            // Günlük hedef süresini alalım:
            val dailyGoalTime = binding.dailyGoalTimeId1.text.toString() + ":" + binding.dailyGoalTimeId2.text.toString()

            val todayDate = binding.todayDateId.text.toString()
            dateRange = "today"
            val date = Timestamp.now().toDate().toString()

            val targetTime = binding.dailyGoalTimeId1.text.toString() + ":" + binding.dailyGoalTimeId2.text.toString()
            val focusTime = "00:00"
            val remainingTime = targetTime
            val success = "%0"

            val username = Firebase.auth.currentUser?.displayName.toString()



            // Database'e ekleyeceğimiz şeyleri bir hashmap içine koyarak ekleyeceğiz.
            // 1.parametre: anahtar kelime (key) - field name. Anahtar kelimelerim hep string olacak çünkü field name'ler string olmak zorunda.
            // 2.parametre: value. (Değer herhangi bir şey olabileceği için Any dedik, Çünkü farklı farklı veri türlerini kaydedebiliriz.)
            val goalMap = hashMapOf<String, Any>()
            // Oluşturduğumuz Map içine eklemelerimizi yapalım:
            // 1.parametre: key
            // 2.parametre: value
            goalMap.put("uuid", uuid)
            goalMap.put("username", username)
            goalMap.put("goalTitle", goalTitle)
            goalMap.put("dailyGoalTime", dailyGoalTime)
            goalMap.put("todayDate", todayDate)
            goalMap.put("dateRange", dateRange)
            goalMap.put("date", date)
            goalMap.put("targetTime", targetTime)
            goalMap.put("focusTime", focusTime)
            goalMap.put("remainingTime", remainingTime)
            goalMap.put("success", success)


            // Şimdi yukarıda aldığımız bilgileri veritabanımıza kaydedelim.
            // collectionPath: collection'ımızın isminin ne olmasını istiyorsak o.
            db.collection("Goals").add(goalMap).addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    // İşlem başarılı ise bu activity'yi sonlandır ve ThinkActivity.kt'ye geri dön:
                    finish()
                }
            }.addOnFailureListener{ exception ->
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }
        if(binding.constraintLayoutIdEveryweek.visibility == View.VISIBLE){

            // Hedef için random bir id oluşturalım:
            val uuid = UUID.randomUUID()
            // Hedef başlığını alalım:
            val goalTitle = binding.goalTitleId.text.toString()
            // Günlük hedef süresini alalım:
            val dailyGoalTime = binding.dailyGoalTimeId1.text.toString() + ":" + binding.dailyGoalTimeId2.text.toString()

            val startDate = binding.startDateId.text.toString()

           // val startDate = Timestamp(Date(binding.startDateId.text.toString())).toDate()
           // val endDate = Timestamp(Date((binding.endDateId.text.toString()))).toDate()

            val endDate = binding.endDateId.text.toString()
            dateRange = "everyweek"
            val date = Timestamp.now()

            var monday = false
            var tuesday = false
            var wednesday = false
            var thursday = false
            var friday = false
            var saturday = false
            var sunday = false

            val targetTime = binding.dailyGoalTimeId1.text.toString() + ":" + binding.dailyGoalTimeId2.text.toString()
            val focusTime = "00:00"
            val remainingTime = targetTime
            val success = "%0"

            val username = Firebase.auth.currentUser?.displayName.toString()

            if (binding.checkBoxMondayId.isChecked == true){
                monday = true
            }
            if (binding.checkBoxTuesdayId.isChecked == true){
                tuesday = true
            }
            if (binding.checkBoxWednesdayId.isChecked == true){
                wednesday = true
            }
            if (binding.checkBoxThursdayId.isChecked == true){
                thursday = true
            }
            if (binding.checkBoxFridayId.isChecked == true){
                friday = true
            }
            if (binding.checkBoxSaturdayId.isChecked == true){
                saturday = true
            }
            if (binding.checkBoxSundayId.isChecked == true){
                sunday = true
            }

            // Database'e ekleyeceğimiz şeyleri bir hashmap içine koyarak ekleyeceğiz.
            // 1.parametre: anahtar kelime (key) - field name. Anahtar kelimelerim hep string olacak çünkü field name'ler string olmak zorunda.
            // 2.parametre: value. (Değer herhangi bir şey olabileceği için Any dedik, Çünkü farklı farklı veri türlerini kaydedebiliriz.)
            val goalMap = hashMapOf<String, Any>()
            // Oluşturduğumuz Map içine eklemelerimizi yapalım:
            // 1.parametre: key
            // 2.parametre: value
            goalMap.put("uuid", uuid)
            goalMap.put("username", username)
            goalMap.put("goalTitle", goalTitle)
            goalMap.put("dailyGoalTime", dailyGoalTime)
            goalMap.put("startDate", startDate)
            goalMap.put("endDate", endDate)
            goalMap.put("dateRange", dateRange)
            goalMap.put("date", date)
            goalMap.put("monday", monday)
            goalMap.put("tuesday", tuesday)
            goalMap.put("wednesday", wednesday)
            goalMap.put("thursday", thursday)
            goalMap.put("friday", friday)
            goalMap.put("saturday", saturday)
            goalMap.put("sunday", sunday)
            goalMap.put("targetTime", targetTime)
            goalMap.put("focusTime", focusTime)
            goalMap.put("remainingTime", remainingTime)
            goalMap.put("success", success)


            // Şimdi yukarıda aldığımız bilgileri veritabanımıza kaydedelim.
            // collectionPath: collection'ımızın isminin ne olmasını istiyorsak o.
            db.collection("Goals").add(goalMap).addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    // İşlem başarılı ise bu activity'yi sonlandır ve ThinkActivity.kt'ye geri dön:
                    finish()
                }
            }.addOnFailureListener{ exception ->
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }

    }

    // tarih seçmek için tıklandığında klavyeyi kapatmak için:
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun updateDate(myCalendar: Calendar){
        val dateFormat = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        binding.todayDateId.setText(simpleDateFormat.format(myCalendar.time))
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

    /*
    fun onClickTodayButton(view : View){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val todayGoalFragment = TodayGoalFragment()
        fragmentTransaction.replace(R.id.frameLayoutTimeID , todayGoalFragment).commit()
    }

    fun onClickEveryweekButton(view : View){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val everyweekGoalFragment = EveryweekGoalFragment()
        fragmentTransaction.replace(R.id.frameLayoutTimeID , everyweekGoalFragment).commit()
    }*/
}