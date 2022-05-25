package com.wadachirebandi.kiddietracking.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.wadachirebandi.kiddietracking.R
import com.wadachirebandi.kiddietracking.daos.DriverDao
import com.wadachirebandi.kiddietracking.daos.UserDao
import com.wadachirebandi.kiddietracking.databinding.ActivityTrackBusBinding
import com.wadachirebandi.kiddietracking.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrackBusActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityTrackBusBinding

    private lateinit var map: GoogleMap

    private var liveLocation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        UserDao().getUser().addOnSuccessListener {
            it.toObject(User::class.java)?.let { it1 -> getBusDriver(it1) }
        }
    }

    private fun getBusDriver(user: User) {
        checkJourney(user.driverUid)
    }

    private fun checkJourney(driverUid: String?) {
        DriverDao().driverCollection.document(driverUid!!).collection("Location")
            .document("ap9IhGYNgDtqUkw8nus1").get().addOnSuccessListener { isLocationEnable ->
                if (isLocationEnable.exists()) {

                    liveLocation = isLocationEnable["live_location"] as Boolean
                    if (liveLocation) {
                        DriverDao().driverCollection.document(driverUid).collection("Location")
                            .document("72AJHIPdt2cVaFDQ2V5T").get().addOnSuccessListener {
                                val locationLat = it["latitude"]
                                val locationLong = it["longitude"]
                                val latLng = LatLng(locationLat as Double, locationLong as Double)
                                map.addMarker(
                                    MarkerOptions().position(latLng)
                                        .title("The BUS is currently here")
                                )
                                val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                                map.moveCamera(update)
                            }
                        CoroutineScope(Dispatchers.IO).launch {
                            delay(5000)
                            checkJourney(driverUid)
                        }
                    } else {
                        binding.textView.visibility = View.VISIBLE
                    }
                } else {
                    binding.textView.visibility = View.VISIBLE
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}