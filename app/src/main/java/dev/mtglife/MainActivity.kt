package dev.mtglife

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private var defaultLife: Int = 20
    private var myLife: Int = defaultLife
    private var theirLife: Int = defaultLife

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val pref = this?.getPreferences(Context.MODE_PRIVATE)
        myLife = pref.getInt(getString(R.string.my_life), defaultLife)
        theirLife = pref.getInt(getString(R.string.their_life), defaultLife)

        // set up down listeners
        myLifePlusButton.setOnClickListener {
            myLife++; update()
        }
        myLifeMinusButton.setOnClickListener {
            myLife--; update()
        }
        theirLifePlusButton.setOnClickListener {
            theirLife++; update()
        }
        theirLifeMinusButton.setOnClickListener {
            theirLife--; update()
        }

        // Set a click listener for button widget
        newGame.setOnClickListener{
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("New game?")
            builder.setMessage("Are sure you want a new game?")
            builder.setPositiveButton("YES"){_, _ ->
                myLife = defaultLife
                theirLife = defaultLife
                update()
            }
            builder.setNegativeButton("Cancel"){_, _ -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun update() {
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt(getString(R.string.my_life), myLife)
            putInt(getString(R.string.their_life), theirLife)
            commit()
        }
        findViewById<TextView>(R.id.myLife).text = myLife.toString()
        findViewById<TextView>(R.id.theirLife).text = theirLife.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}