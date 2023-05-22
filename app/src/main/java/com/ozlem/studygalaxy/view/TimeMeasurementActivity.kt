package com.ozlem.studygalaxy.view

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.adapter.GoalRecyclerAdapter
import com.ozlem.studygalaxy.databinding.ActivityTimeMeasurementBinding
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

    // document path'i almak için:
    var documentPath : String = "0"

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



            // Şimdi süre kaydetme işlemini yapalım. Firestore'a data gönderelim:
            // session time'ı alalım:
            val sessionTimeValue = binding.chronometerId.text.toString()

            var sessionTimeValueList = sessionTimeValue.split(":")
            var sessionHour = sessionTimeValueList[0].toInt()
            var sessionSecond = sessionTimeValueList[1].toInt()

            // target time'ı alalım:
            val targetTimeValue = binding.goalTargetTimeId.text.toString()

            var targetTimeValueList = targetTimeValue.split(":")
            var targetHour = targetTimeValueList[0].toInt()
            var targetSecond = targetTimeValueList[1].toInt()

            // focus time'ı alalım:
            val focusTimeValue = binding.goalFocusTimeId.text.toString()

            var focusTimeValueList = focusTimeValue.split(":")
            var focusHour = focusTimeValueList[0].toInt()
            var focusSecond = focusTimeValueList[1].toInt()

            var focusTimeHour = focusHour.toInt() + sessionHour.toInt()
            var focusTimeSecond = focusSecond.toInt() + sessionSecond.toInt()

            // dakikaları saaate ekleme:
            focusTimeHour += focusTimeSecond % 60
            focusTimeSecond -= (focusTimeSecond % 60) * 60

            val newFocusTime = focusTimeHour.toString() + ":" + focusTimeSecond.toString()

            // Remaining Time Hesapları:
            val remainingTimeValue = binding.goalRemainingTimeId.text.toString()

            var remainingTimeValueList = remainingTimeValue.split(":")
            var remainingHour = remainingTimeValueList[0].toInt()
            var remainingSecond = remainingTimeValueList[1].toInt()

            var remainingTimeHour = targetHour.toInt() - focusTimeHour.toInt()
            var remainingTimeSecond = targetSecond.toInt() + focusTimeSecond.toInt()

            val newRemainingTime = remainingTimeHour.toString() + ":" + remainingTimeSecond.toString()


            // adapter'dan goalTitle değerini çekelim:
            val valueGoalTitle = intent.getStringExtra("valueGoalTitle")

            // güncel
            valueGoalTitle?.let { it ->
                db.collection("Goals").document(documentPath)
                    .update(
                        mapOf(
                            "focusTime" to newFocusTime,
                            "remainingTime" to newRemainingTime,
                        ),
                    )
            }




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


            if(firebase_get_data()!=null){
                firebase_get_data()
            }


            // recyclerViewAdapter'ı initialize edelim:
            recyclerViewAdapter = GoalRecyclerAdapter(goalList)


        }
        else{
            // No user is signed in
            Toast.makeText(this, "user null döndü", Toast.LENGTH_LONG).show()
        }

    }

    fun firebase_get_data(){

        if(db.collection("Goals")!=null){

            db.collection("Goals").whereEqualTo("username", user?.displayName).addSnapshotListener{ snapshot, error ->


                if(error != null){
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                }else{
                    // Hata mesajı yoksa büyük ihtimalle snapshot'ımız gelmiştir.
                    // Fakat snapshot bize ? yani nullable olarak geliyor. Bu yüzden if ekleyelim:
                    if(snapshot != null){
                        // Burada snapshot'ım null olmayabilir ama içinde bir doküman olmayabilir.
                        // isEmpty() ile gittiğimiz cllection'ın içinde bir document var mı yok mu öğrenebiliriz.
                        // Kontrol ediyoruz çünkü içinde document olmayan bir collection'ada gitmiş olabiliriz.
                        // Eğer boşsa true döner:
                        if(!snapshot.isEmpty){
                            // Hem snapshot null değil, hem hata mesajı yok hem de içinde document var:
                            // Aşağıdaki documents değişkeni collection içindeki tüm document'ları barındıran bir dizi:
                            val documents = snapshot.documents
                            // for loop'a girmeden önce temizledik. Eğer temizlemeseydik her bir paylaşım olduğunda üstüne yazacaktı
                            // ve bir sürü paylaşım gözükecekti:
                            goalList.clear()

                            for (document in documents){
                                // Bu for loop'un içinde document'lara tek tek ulaşalım.
                                // Any geliyordu String'e çevirmek için as String

                                // Burada günlük hedefleri çektik:
                                val goalTitle = document.get("goalTitle") as String?
                                val dateRange = document.get("dateRange") as String?
                                val success = document.get("success") as String?
                                val focusTime = document.get("focusTime") as String?
                                val targetTime = document.get("targetTime") as String?
                                val remainingTime = document.get("remainingTime") as String?
                                val sessionTime = document.get("sessionTime") as String?


                                // adapter'dan goalTitle değerini çekelim:
                                val valueGoalTitle = intent.getStringExtra("valueGoalTitle")
                                // ve firestore'daki goalTitle değeri ile karşılaştıralım:
                                if(goalTitle == valueGoalTitle){
                                    binding.pageNameId.setText(goalTitle)

                                    // Target Time'ı çekelim:
                                    binding.goalTargetTimeId.setText(targetTime)
                                    // Focus Time'ı çekelim:
                                    binding.goalFocusTimeId.setText(focusTime)
                                    // Remaining Time'ı çekelim:
                                    binding.goalRemainingTimeId.setText(remainingTime)

                                    documentPath = document.id
                                }


                                val downloadedGoals = Goals(goalTitle, dateRange, success, focusTime, targetTime, remainingTime, sessionTime)
                                goalList.add(downloadedGoals)
                            }
                            // Yeni veri geldi haberin olsun diyoruz böylece recylerView verileri göstermeye çalışacak:
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }


            }
        }

       // veri çekme tamamlandı


        }

}

