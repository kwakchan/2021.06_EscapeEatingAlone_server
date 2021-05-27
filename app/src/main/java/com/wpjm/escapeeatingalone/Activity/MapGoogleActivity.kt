package com.wpjm.escapeeatingalone.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.wpjm.escapeeatingalone.R


class MapGoogleActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_google)
        Places.initialize(applicationContext, "AIzaSyAOiIgD5-titJQRG5jgT_oKgmQl_PfuaVw")

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        val placesClient = Places.createClient(this)
        val zoom: CameraUpdate = CameraUpdateFactory.zoomTo(16f) // 범위 높을수록 확대가 커집니다.

        mapFragment.getMapAsync(this)
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.TYPES)) // Specify the types of place data to return.

        // place search 핸들러
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.e(
                    "googleMAP",
                    "Place: ${place.name}, ${place.id}"
                )

                mMap.addMarker(MarkerOptions().position(place.latLng!!).title(place.name!!).snippet("파티원 구하기"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng!!))
                mMap.animateCamera(zoom) // 줌을 당긴다
            }

            override fun onError(status: Status) {
                Log.e("googleMAP", "An error occurred: $status")
            }
        })
    }

    // 구글맵 시작 설정
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val busan = LatLng(35.1, 129.0)
        val zoom: CameraUpdate = CameraUpdateFactory.zoomTo(16f) // 범위 높을수록 확대가 커집니다.

        mMap.addMarker(MarkerOptions().position(busan).title("Marker in Busan"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(busan))
        mMap.animateCamera(zoom)
        googleMap.setOnInfoWindowClickListener(this)
    }

    // 마커 위 정보창 클릭할 때
    override fun onInfoWindowClick(marker: Marker?) {
        Toast.makeText(this, "Info window clicked", Toast.LENGTH_SHORT).show()
        Log.e("googleMap","${marker!!.title}, ${marker!!.id}")
        var intent = Intent(this, PartyActivity::class.java)
        intent.putExtra("storeName", marker!!.title)
        startActivity(intent)

    }

    // Intent function
    private fun gotoActivity(c: Class<*>) {
        var intent = Intent(this, c)
        startActivity(intent)
    }
}