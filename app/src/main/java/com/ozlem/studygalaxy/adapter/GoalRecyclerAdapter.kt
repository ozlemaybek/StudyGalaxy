package com.ozlem.studygalaxy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ozlem.studygalaxy.R
import com.ozlem.studygalaxy.model.Goals



class GoalRecyclerAdapter (val goalList : ArrayList<Goals>) : RecyclerView.Adapter<GoalRecyclerAdapter.GoalHolder>(){

    // RecyclerView.ViewHolder sınıfı bizden bir view istiyor.
    // Bu view'uda constructor'ın içinde alalım (itemview)
    class GoalHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    // recycler_row.xml ile RecyclerView'u birbirine bağlayan fonksiyondur:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        // ViewHolder oluşturulduğunda ne yapılacağını bu metodun içine yazıyoruz.
        // Bir xml ile buradaki kodu bağlarken inflater kullanıyorduk.
        // from içinde context'i vermemiz gerekiyor.
        // parent bize hangi ana grup içinde bulunduğumuzu gösteriyor. Ve parent zaten bu metodda veriliyor.
        // parent.context diyerek içinde bulunduğumuz context'i alabiliyoruz.
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.goals_recycler_row,parent,false)
        // Bizden bir viewHolder döndürmemiz isteniyor.
        return GoalHolder(view)
    }

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        TODO("Not yet implemented")

        // kotlin syntatic problemini bu şekilde çözdüm:
        holder.itemView.findViewById<TextView>(R.id.percentage_of_completion_id).text = goalList[position].percentageOfCompletion
        holder.itemView.findViewById<TextView>(R.id.total_daily_time_id).text = goalList[position].totalDailyTime
     }

    // recylerView'da kaç tane row göstereceğimizi belirliyoruz.
    // Mesela return 5 dersek 5 tane satır gösterecek.
    override fun getItemCount(): Int {
        // Kaç tane goal varsa recyclerview'da o kadar satır olacak:
        return goalList.size
    }
}