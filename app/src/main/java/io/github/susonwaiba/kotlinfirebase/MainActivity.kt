package io.github.susonwaiba.kotlinfirebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_submit.setOnClickListener {
            saveHero()
        }

        button_featch_page.setOnClickListener {
            val intent = Intent(this, FetchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveHero() {
        val name = editText_name.text.toString().trim()
        if (name.isEmpty()) {
            editText_name.error = "Please enter a name"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("heroes")
        val heroId = ref.push().key

        val hero = Hero(heroId, name, ratingBar.rating.toInt())

        ref.child(heroId).setValue(hero).addOnCompleteListener {
            Toast.makeText(applicationContext, "io.github.susonwaiba.kotlinfirebase.Hero saved successfully", Toast.LENGTH_LONG).show()
            editText_name.text = null
            val ratingValue = 0
            ratingBar.rating = ratingValue.toFloat()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "Action failed", Toast.LENGTH_LONG).show()
        }
    }
}
