package com.ozlem.studygalaxy.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.adapter.GoalRecyclerAdapter
import com.ozlem.studygalaxy.databinding.FragmentHomeBinding
import com.ozlem.studygalaxy.model.AllGoals
import com.ozlem.studygalaxy.model.Goals


class HomeFragment : Fragment() {

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    // Database objemizi oluşturalım:
    val db = Firebase.firestore
    // view binding için:
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    // sharingLsit bir arraylist olacak ve içinde Sharing objelerini tutacak:
    var goalList = ArrayList<Goals>()
    var allGoalList = ArrayList<AllGoals>()
    // RecyclerView Adapter'ımızı tanımlayalım:
    // recyclerViewAdapter bir GoalRecylerAdapter olacak:
    private lateinit var recyclerViewAdapter : GoalRecyclerAdapter


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

            //firebase_get_data()

            // Bir layout manager oluşturduk.
            // Bu şunu sağlıyor: RecylerView'da elemanlar alt alta mı gösterilsin grid layout olarakmı gösterilsin bunu seçiyoruz.
            // Biz alt alta göstereceğiz.
            val layoutManager = LinearLayoutManager(getActivity())
            binding.recyclerView0Id.layoutManager = layoutManager

            // recyclerViewAdapter'ı initialize edelim:
            recyclerViewAdapter = GoalRecyclerAdapter(goalList)
            binding.recyclerView0Id.adapter = recyclerViewAdapter

            // Kullanıcı ilk kez oturum açıyorsa default bir hedef oluşturmalıyız:

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


            /*val intent = Intent(getActivity(), TimeMeasurementActivity::class.java)
            startActivity(intent)*/
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

    fun firebase_get_data(){
        // Database'den veri çekelim:

        // PART 1:
        // Home Page'deki tüm hedeflere dair bilgileri çekiyoruz:
        db.collection("AllGoals").addSnapshotListener{ snapshot , error ->

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
                        allGoalList.clear()
                        for (document in documents){
                            // Bu for loop'un içinde document'lara tek tek ulaşalım.
                            // Any geliyordu String'e çevirmek için as String dedik:
                            val focusTime = document.get("focusTime") as String
                            val success = document.get("success") as String
                            val targetTime = document.get("targetTime") as String?
                            val downloadedAllGoals = AllGoals(focusTime, success, targetTime)
                            allGoalList.add(downloadedAllGoals)
                        }
                        // Yeni veri geldi haberin olsun diyoruz böylece recylerView verileri göstermeye çalışacak:
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        // PART 2:
        // Şimdi ana sayfada bulunan kullanıcıya ait hedef listesi ile ilgili verileri çekelim:
        db.collection("Goals").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener{ snapshot, error ->

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
                            // Any geliyordu String'e çevirmek için as String dedik:
                            val goalTitle = document.get("goalTitle") as String
                            val percentageOfCompletion = document.get("percentageOfCompletion") as String
                            val remainingTime = document.get("remainingTime") as String
                            val sessionTime = document.get("sessionTime") as String
                            val targetTime = document.get("targetTime") as String?
                            val timePeriod = document.get("timePeriod") as String?
                            val totalDailyTime= document.get("totalDailyTime") as String?
                            val totalTime = document.get("totalTime") as String?

                            val downloadedGoals = Goals(goalTitle, percentageOfCompletion, remainingTime, sessionTime,
                                targetTime, timePeriod, totalDailyTime, totalTime)
                            goalList.add(downloadedGoals)
                        }
                        // Yeni veri geldi haberin olsun diyoruz böylece recylerView verileri göstermeye çalışacak:
                        recyclerViewAdapter.notifyDataSetChanged()
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