package com.ozlem.studygalaxy.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.adapter.GoalRecyclerAdapter
import com.ozlem.studygalaxy.databinding.FragmentGoalsBinding
import com.ozlem.studygalaxy.databinding.FragmentHomeBinding
import com.ozlem.studygalaxy.model.AllGoals
import com.ozlem.studygalaxy.model.Goals

class GoalsFragment : Fragment() {

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    // Database objemizi oluşturalım:
    val db = Firebase.firestore
    // view binding için:
    private var _binding: FragmentGoalsBinding? = null
    private val binding get() = _binding!!

    // goalList bir arraylist olacak ve içinde Sharing objelerini tutacak:
    var goalList = ArrayList<Goals>()

    // RecyclerView Adapter'ımızı tanımlayalım:
    // recyclerViewAdapter bir GoalRecylerAdapter olacak:
    private lateinit var recyclerViewAdapter : GoalRecyclerAdapter
    // Güncel kullanıcı (uygulamaya şuanda giriş yapmış olan kullanıcı)'yı alalım:
    val user = Firebase.auth.currentUser


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
        _binding = FragmentGoalsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Güncel kullanıcı (uygulamaya şuanda giriş yapmış olan kullanıcı)'yı alalım:
        //val user = Firebase.auth.currentUser
        if(user != null){

            // Güncel tarihi firebase'den çekelim:
            // Tüm detaylarıyla çekmek istersek aşağıdaki yorum satırı ile yapıyoruz bunu veri tabanı için kullanacağım:
            // binding.todayDateId.setText(Timestamp.now().toDate().toString())

            val strList = Timestamp.now().toDate().toString().split(" ")
            val dateValue = strList[2] + " " + strList[0] + " " + strList[1]
            // binding.todayDateId.setText(dateValue)

            val timeList = Timestamp.now().toDate().toString().split(" ")
            val timeValue = strList[3].split(":")

            if(firebase_get_data()!=null){
                firebase_get_data()
            }

            // Bir layout manager oluşturduk.
            // Bu şunu sağlıyor: RecylerView'da elemanlar alt alta mı gösterilsin grid layout olarakmı gösterilsin bunu seçiyoruz.
            // Biz alt alta göstereceğiz.
            val layoutManager = LinearLayoutManager(getActivity())
            binding.recyclerView0Id.layoutManager = layoutManager

            // recyclerViewAdapter'ı initialize edelim:
            recyclerViewAdapter = GoalRecyclerAdapter(goalList)
            binding.recyclerView0Id.adapter = recyclerViewAdapter

        }
        else{
            // No user is signed in
            Toast.makeText(getActivity(), "user null döndü", Toast.LENGTH_LONG).show()
        }









    }

    fun firebase_get_data(){
        // Database'den veri çekelim:

        // Veri çekmeden önce hedef listesini temizleyelim:
        goalList.clear()

        if(db.collection("Goals")!=null){

            // Şimdi ana sayfada bulunan kullanıcıya ait hedef listesi ile ilgili verileri çekelim:

            // TODAY GOALS
            var strList = Timestamp.now().toDate().toString().split(" ")
            var dateList = Array<String>(3){"0"}

            dateList[0] = strList[2]
            dateList[2] = strList[5]

            if(strList[1] == "Jan"){
                dateList[1] = "01"
            }
            if(strList[1] == "Feb"){
                dateList[1] = "02"
            }
            if(strList[1] == "Mar"){
                dateList[1] = "03"
            }
            if(strList[1] == "Apr"){
                dateList[1] = "04"
            }
            if(strList[1] == "May"){
                dateList[1] = "05"
            }
            if(strList[1] == "Jun"){
                dateList[1] = "06"
            }
            if(strList[1] == "Jul"){
                dateList[1] = "07"
            }
            if(strList[1] == "Aug"){
                dateList[1] = "08"
            }
            if(strList[1] == "Sep"){
                dateList[1] = "09"
            }
            if(strList[1] == "Oct"){
                dateList[1] = "10"
            }
            if(strList[1] == "Nov"){
                dateList[1] = "11"
            }
            if(strList[1] == "Dec"){
                dateList[1] = "12"
            }

            // bugünün tarihi:
            val dateTime = dateList[0]+ "-" + dateList[1] + "-" + dateList[2]

            // Bugün için tanımlı hedefleri çekiyoruz:
            // kullanıcının sadece o gün olan hedefleri
            // db.collection("Goals").whereEqualTo("todayDate",dateTime).whereEqualTo("username", user?.displayName).addSnapshotListener{ snapshot, error ->
            // Kullanıcının zaman aralığı farketmeksizin tüm hedefleri:
            db.collection("Goals").whereEqualTo("username", user?.displayName).addSnapshotListener{ snapshot, error ->

                if(error != null){
                    Toast.makeText(getActivity(), error.localizedMessage, Toast.LENGTH_LONG).show()
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}