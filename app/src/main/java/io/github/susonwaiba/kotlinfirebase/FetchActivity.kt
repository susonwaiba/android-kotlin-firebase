package io.github.susonwaiba.kotlinfirebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_fetch.*

class FetchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView_heroes.layoutManager = LinearLayoutManager(this)
        showHeroes()
    }

    private fun showHeroes() {
        val ref = FirebaseDatabase.getInstance().getReference("heroes")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                textView_loading.visibility = View.INVISIBLE
                val heroes = mutableListOf<Hero>()

                if (p0!!.exists()) {
                    textView_nothing_found.visibility = View.INVISIBLE
                    for (item in p0.children) {
                        val hero = item.getValue(Hero::class.java)
                        heroes.add(hero!!)
                    }
                } else {
                    textView_nothing_found.visibility = View.VISIBLE
                }

                runOnUiThread {
                    recyclerView_heroes.adapter = HeroAdapter(heroes)
                }

            }

            override fun onCancelled(p0: DatabaseError?) {
                println("Firebase failed to fetch")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
