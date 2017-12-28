package io.github.susonwaiba.kotlinfirebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra("id")

        val ref = FirebaseDatabase.getInstance().getReference("heroes/" + id)
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                val id = p0?.child("id")?.value
                val name = p0?.child("name")?.value
                val rating = p0?.child("rating")?.value

                editText_name.setText(name.toString())
                val ratingValue = rating.toString()
                ratingBar.rating = ratingValue.toFloat()

                textView_loading.visibility = View.INVISIBLE
            }

            override fun onCancelled(p0: DatabaseError?) {
                println("Firebase failed to fetch")
            }
        })

        button_update.setOnClickListener {
            saveHero(id)
        }
    }

    private fun saveHero(id: String) {
        val name = editText_name.text.toString().trim()
        if (name.isEmpty()) {
            editText_name.error = "Please enter a name"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("heroes")
        val hero = Hero(id, name, ratingBar.rating.toInt())

        ref.child(id).setValue(hero).addOnCompleteListener {
            Toast.makeText(applicationContext, "io.github.susonwaiba.kotlinfirebase.Hero updated successfully", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "Action failed", Toast.LENGTH_LONG).show()
        }
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
