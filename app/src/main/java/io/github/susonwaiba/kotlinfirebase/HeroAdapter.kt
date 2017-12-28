package io.github.susonwaiba.kotlinfirebase

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.row_hero.view.*

/**
 * Created by suson on 12/26/2017.
 */
class HeroAdapter(val heroList: MutableList<Hero>): RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return heroList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_hero, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
        val hero = heroList[position]
        holder?.view?.textView_name?.text = hero.name
        holder?.view?.textView_rating?.text = hero.rating.toString()

        holder?.view?.row_hero?.setOnClickListener {
            val intent = Intent(holder?.view?.context, EditActivity::class.java)
            intent.putExtra("id", hero.id)
            holder?.view?.context.startActivity(intent)
        }

        holder?.view?.floatingActionButton_delete?.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("heroes")
            ref.child(hero.id).removeValue().addOnCompleteListener {
                Toast.makeText(holder.view.context, "Delete successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(holder.view.context, "Action failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}