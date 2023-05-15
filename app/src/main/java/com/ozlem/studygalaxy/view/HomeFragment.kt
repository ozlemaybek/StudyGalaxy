package com.ozlem.studygalaxy.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
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

            // Güncel tarihi firebase'den çekelim:
            // Tüm detaylarıyla çekmek istersek aşağıdaki yorum satırı ile yapıyoruz bunu veri tabanı için kullanacağım:
            // binding.todayDateId.setText(Timestamp.now().toDate().toString())

            val strList = Timestamp.now().toDate().toString().split(" ")
            val dateValue = strList[2] + " " + strList[0] + " " + strList[1]
            binding.todayDateId.setText(dateValue)

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
            }

            //Toast.makeText(getActivity(), "Kullanıcı id: +${user!!.uid} + username: ${user!!.displayName}", Toast.LENGTH_LONG).show()
            // User is signed in
            // İlgili alanlara bilgileri bastıralım:
            binding.usernameId.text = user.displayName


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

        //Herhangi bir imageView ile popup menu (hedeflerin menüsü için: ama şu anda document id'yi elde edemedim.)
        /*
        binding.recyclerView0Id.findViewById<ImageView>(R.id.options_button_id).setOnClickListener {
            val popupMenu = PopupMenu(getActivity(),it)
            popupMenu.menuInflater.inflate(R.menu.goals_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{ item ->
                when(item.itemId) {
                    /*R.id.complete_option_id -> {
                        //val intent = Intent(getActivity(), EditProfileActivity::class.java)
                        //startActivity(intent)
                        true
                    }
                    R.id.edit_option_id -> {
                        //val intent = Intent(getActivity(), ChangePasswordActivity::class.java)
                        //startActivity(intent)
                        true
                    }*/
                    R.id.delete_option_id -> {
                        //val intent = Intent(getActivity(), DeleteAccountActivity::class.java)
                        //startActivity(intent)
                        //Toast.makeText(getActivity(), "Goal deleted!", Toast.LENGTH_LONG).show()


                        db.collection("Goals").document("DC")
                            .delete()
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                        true
                    }
                }
                true
            }
            popupMenu.show()

        }*/

        // onViewCreated burada bitiyor
    }

    fun firebase_get_data(){
        // Database'den veri çekelim:

        // PART 1:
        // Home Page'deki tüm hedeflere dair bilgileri çekiyoruz:

        /*
        if(db.collection("AllGoals") != null){
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
                                val focusTime = document.get("focusTime") as String?
                                val success = document.get("success") as String?
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
        }*/

        if(db.collection("Goals")!=null){
            // PART 2:
            // Şimdi ana sayfada bulunan kullanıcıya ait hedef listesi ile ilgili verileri çekelim:

            //val strList = Timestamp.now().toDate().toString().split(" ")
            //val dateValue = strList[2] + "-" + strList[0] + "-" + strList[1]
            // .whereEqualTo("todayDate",dateValue)

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
                                // Any geliyordu String'e çevirmek için as String
                                val goalTitle = document.get("goalTitle") as String?
                                val dateRange = document.get("dateRange") as String?
                                val success = document.get("success") as String?
                                val focusTime = document.get("focusTime") as String?
                                val targetTime = document.get("targetTime") as String?

                                val downloadedGoals = Goals(goalTitle, dateRange, success, focusTime, targetTime)
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