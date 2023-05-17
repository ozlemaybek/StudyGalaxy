package com.ozlem.studygalaxy.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.adapter.GoalRecyclerAdapter
import com.ozlem.studygalaxy.databinding.ActivityTimeMeasurementBinding
import com.ozlem.studygalaxy.model.AllGoals
import com.ozlem.studygalaxy.model.Goals

class TimeMeasurementActivity : AppCompatActivity() {

    // view binding için :
    private lateinit var binding: ActivityTimeMeasurementBinding

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth

    // Database objemizi oluşturalım:
    val db = Firebase.firestore

    // sharingLsit bir arraylist olacak ve içinde Sharing objelerini tutacak:
    var goalList = ArrayList<Goals>()

    // RecyclerView Adapter'ımızı tanımlayalım:
    // recyclerViewAdapter bir GoalRecylerAdapter olacak:
    private lateinit var recyclerViewAdapter : GoalRecyclerAdapter
    // Güncel kullanıcı (uygulamaya şuanda giriş yapmış olan kullanıcı)'yı alalım:
    val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // view binding için :
        binding = ActivityTimeMeasurementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth

        // İlk değerini sıfır yapıyoruz çünkü elimizde başlangıçta zaman yok.
        var stopTime : Long = 0

        binding.startButtonId.setOnClickListener {
            // sistem saatini alalım:
            // aşağıdaki satır sayesinde sistem saatini alıp kronometre içerisine aktarabiliyoruz:
            binding.chronometerId.base = SystemClock.elapsedRealtime() + stopTime

            // burada şuna dikkat etmeliyiz kronometreyi başlattık
            // 10 sn saydı durdurduk ve tekrar başlattık 11 12 diye gitmesi lazım
            // durdurduğumuz süreyi kronometrenin ekleyip devam etmesi lazım
            // o zamanda stopTime değişkeninde tutuluyor.

            // şimdi kronometreyi çalıştıralım:
            binding.chronometerId.start()
            // kronometreyi başlattığımız zaman start butonunu pasif kılmalıyız:
            binding.startButtonId.visibility = View.GONE
            // ve pause butonunu aktive etmeliyiz:
            binding.pauseButtonId.visibility = View.VISIBLE
        }

        binding.pauseButtonId.setOnClickListener{
            stopTime = binding.chronometerId.base-SystemClock.elapsedRealtime()
            // şimdi kronometreyi durduralım:
            binding.chronometerId.stop()

            // kronometreyi durdurduğumuz zaman pause butonunu pasif kılmalıyız:
            binding.pauseButtonId.visibility = View.GONE
            // ve start butonunu aktive etmeliyiz:
            binding.startButtonId.visibility = View.VISIBLE
        }

        // Güncel kullanıcı (uygulamaya şuanda giriş yapmış olan kullanıcı)'yı alalım:
        //val user = Firebase.auth.currentUser
        if(user != null){

            // Güncel tarihi firebase'den çekelim:
            // Tüm detaylarıyla çekmek istersek aşağıdaki yorum satırı ile yapıyoruz bunu veri tabanı için kullanacağım:
            // binding.todayDateId.setText(Timestamp.now().toDate().toString())

            /*val strList = Timestamp.now().toDate().toString().split(" ")
            val dateValue = strList[2] + " " + strList[0] + " " + strList[1]
            // binding.todayDateId.setText(dateValue)

            val timeList = Timestamp.now().toDate().toString().split(" ")
            val timeValue = strList[3].split(":")
            if(timeValue[0].toInt() >= 5 && timeValue[0].toInt() <= 12){
                binding.textView3.setText("Good Morning")
            }
            if(timeValue[0].toInt() > 12 && timeValue[0].toInt() < 18){
                binding.textView3.setText("Good Afternoon")
            }
            if(timeValue[0].toInt() >= 18 && timeValue[0].toInt() <= 23){
                binding.textView3.setText("Good Evening")
            }
            if(timeValue[0].toInt() < 5){
                binding.textView3.setText("Good Evening")
            }*/

            //Toast.makeText(getActivity(), "Kullanıcı id: +${user!!.uid} + username: ${user!!.displayName}", Toast.LENGTH_LONG).show()
            // User is signed in
            // İlgili alanlara bilgileri bastıralım:
            //binding.usernameId.text = user.displayName


 /*           if(firebase_get_data()!=null){
                firebase_get_data()
            }
*/
            // Bir layout manager oluşturduk.
            // Bu şunu sağlıyor: RecylerView'da elemanlar alt alta mı gösterilsin grid layout olarakmı gösterilsin bunu seçiyoruz.
            // Biz alt alta göstereceğiz.
            /*
            val layoutManager = LinearLayoutManager(getActivity())
            binding.recyclerView0Id.layoutManager = layoutManager

            // recyclerViewAdapter'ı initialize edelim:
            recyclerViewAdapter = GoalRecyclerAdapter(goalList)
            binding.recyclerView0Id.adapter = recyclerViewAdapter*/

        }
        else{
            // No user is signed in
            Toast.makeText(this, "user null döndü", Toast.LENGTH_LONG).show()
        }

    }

    fun firebase_get_data(){

        // Database'den veri çekelim:
        val documentID = db.collection("Goals").document().getId()
        val docRef = db.collection("Goals").document(documentID)

        if(documentID !=null){
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                        val goalTitle = document.get("goalTitle") as String?
                        val dateRange = document.get("dateRange") as String?
                        val success = document.get("success") as String?
                        val focusTime = document.get("focusTime") as String?
                        val targetTime = document.get("targetTime") as String?
                        val remainingTime = document.get("remainingTime") as String?
                        val sessionTime = document.get("sessionTime") as String?

                        // Önce hedef ismini yazdıralım:
                        binding.pageNameId.setText(goalTitle)

                        var goalTargetTimeHour : Int = 0
                        var goalTargetTimeSecond : Int = 0
                        var goalFocusTimeHour : Int = 0
                        var goalFocusTimeSecond : Int = 0
                        // var goalSuccess : Int = 0
                        var goalSessionTimeHour : Int = 0
                        var goalSessionTimeSecond : Int = 0
                        var goalRemainingTimeHour : Int = 0
                        var goalRemainingTimeSecond : Int = 0


                        // Şimdi targetTime'ı çekelim:
                        // CALCULATE TARGET TIME (ALL OF GOALS)
                        val targetTimeList = targetTime.toString().split(":")
                        goalTargetTimeHour += targetTimeList[0].toInt()
                        goalTargetTimeSecond += targetTimeList[1].toInt()

                        // dakikaları saaate ekleme:
                        goalTargetTimeHour += goalTargetTimeSecond % 60
                        goalTargetTimeSecond -= (goalTargetTimeSecond % 60) * 60

                        var hourTarget  : String = "0"
                        var secondTarget : String = "0"
                        if (goalTargetTimeHour.toString().length < 2){
                            hourTarget = "0" + goalTargetTimeHour.toString()
                        }
                        else{
                            hourTarget = goalTargetTimeHour.toString()
                        }
                        if (goalTargetTimeSecond.toString().length < 2){
                            secondTarget = "0" + goalTargetTimeSecond.toString()
                        }
                        else{
                            secondTarget = goalTargetTimeSecond.toString()
                        }

                        val newTargetTime = hourTarget + ":"+ secondTarget

                        binding.goalTargetTimeId.setText(targetTime)

                        // CALCULATE FOCUS TIME (ALL OF GOALS)
                        val focusTimeList = focusTime.toString().split(":")
                        goalFocusTimeHour += focusTimeList[0].toInt()
                        goalFocusTimeSecond += focusTimeList[1].toInt()

                        // dakikaları saaate ekleme:
                        goalFocusTimeHour += goalFocusTimeSecond % 60
                        goalFocusTimeSecond -= goalFocusTimeSecond % 60

                        var hourFocus  : String = "0"
                        var secondFocus : String = "0"
                        if (goalFocusTimeHour.toString().length < 2){
                            hourFocus = "0" + goalFocusTimeHour.toString()
                        }
                        if(goalFocusTimeSecond.toString().length < 2){
                            secondFocus = "0" + goalFocusTimeSecond.toString()
                        }

                        val newFocusTime = hourFocus + ":"+ secondFocus

                        binding.goalFocusTimeId.setText(newFocusTime)




                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }


        }

}

