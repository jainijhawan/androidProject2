package com.example.googlemaps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        addFragment(MapFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.googleMaps -> { // do nothing
                true
            }

            R.id.googlePlaces -> {
                val i = Intent(this, GooglePlacesActivity::class.java)
                this.startActivity(i)
                true
            }

            R.id.email -> {
                val sharedPref =  getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                val latitude = sharedPref.getString(getString(R.string.saved_latitude), null)
                val longitude = sharedPref.getString(getString(R.string.saved_longitude), null)
                val address = sharedPref.getString(getString(R.string.saved_address), null)

                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_SUBJECT, "Current location")
                intent.putExtra(Intent.EXTRA_TEXT, "Latitude is $latitude\nLongitude is $longitude\nAddress is $address");
                startActivity(intent)
                true
            }

            R.id.about -> {
                val i = Intent(this, AboutActivity::class.java)
                this.startActivity(i)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFragment(fragment: Fragment?) {
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.addToBackStack(null)
        fragment?.let { ft.replace(R.id.frameLayout, it, null) }
        ft.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}
