package com.example.googlemaps

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.errors.ApiException
import com.google.maps.model.PlacesSearchResponse
import com.google.maps.model.PlacesSearchResult
import java.io.IOException

class GooglePlacesActivity : AppCompatActivity() {
    private var latitude: String? = null
    private var longitude: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_places)

        val sharedPref =  getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        latitude = sharedPref.getString(getString(R.string.saved_latitude), null)
        longitude = sharedPref.getString(getString(R.string.saved_longitude), null)
        if (latitude == null || longitude == null) return
        val placesSearchResults: Array<PlacesSearchResult> = run().results

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val nearbyPlacesAdapter = NearbyPlacesAdapter(placesSearchResults)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = nearbyPlacesAdapter
    }

    private fun run(): PlacesSearchResponse {

        var request = PlacesSearchResponse()
        val context: GeoApiContext = GeoApiContext.Builder()
            .apiKey(getString(R.string.api_key))
            .build()
        val location = com.google.maps.model.LatLng(latitude?.toDouble()!!, longitude?.toDouble()!!)
        try {
            request = PlacesApi.nearbySearchQuery(context, location)
                .radius(5000)
                .await()
        } catch (e: ApiException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            return request
        }
    }
}