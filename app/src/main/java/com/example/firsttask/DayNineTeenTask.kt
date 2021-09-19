package com.example.firsttask

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_day_nine_teen_task.*
import java.util.*


class DayNineTeenTask : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
     var currentLocation:Location?=null
     var fuesdLocation:FusedLocationProviderClient?=null
    var currentMarker:Marker?=null
    lateinit var editText: EditText

    companion object{
        const val LOCATION_REQUEST=1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_nine_teen_task)

        fuesdLocation=LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()

        editText=findViewById(R.id.place_search)
        if(!Places.isInitialized())
            Places.initialize(applicationContext, "AIzaSyCF9SORD3XLYlfd5t-wjy37JuV5nOzjerc")

        editText.isFocusable=false

        editText.setOnClickListener({
            val fieldList: List<Place.Field> = Arrays.asList(Place.Field.ADDRESS,
                    Place.Field.LAT_LNG, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
                    .build(this@DayNineTeenTask)
            startActivityForResult(intent, 100)
        })


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode== RESULT_OK){
            val place=Autocomplete.getPlaceFromIntent(data!!)
            editText.setText(place.address)
            locationText(place.address.toString())
            drawMarker(place.latLng!!)
        }
        else if(resultCode==AutocompleteActivity.RESULT_ERROR){
           val status: Status=Autocomplete.getStatusFromIntent(data!!)
            Toast.makeText(applicationContext, status.statusMessage, Toast.LENGTH_SHORT).show()

        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latlong=LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)

        drawMarker(latlong)

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(p0: Marker) {
            }

            override fun onMarkerDrag(p0: Marker) {
            }
            override fun onMarkerDragEnd(p0: Marker) {
                if (currentMarker != null)
                    currentMarker?.remove()
                val newLatLag = LatLng(p0?.position!!.latitude, p0?.position.longitude)
                drawMarker(newLatLag)

            }

        })

    }

    private fun drawMarker(latLng: LatLng) {
        val markerOptions=MarkerOptions().position(latLng).title("Your here")
                .snippet(getAddress(latLng.latitude, latLng.longitude)).draggable(true)
        locationText(getAddress(latLng.latitude, latLng.longitude).toString())

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        currentMarker= mMap.addMarker(markerOptions)
        currentMarker?.showInfoWindow()

    }

    private fun getAddress(latitude: Double, latitude1: Double): String? {

        val geocoder=Geocoder(this, Locale.getDefault())
       val address= geocoder.getFromLocation(latitude, latitude1, 1)
        val city=address.get(0).locality.toString()
        val add=address.get(0).getAddressLine(0).toString()
        val value=city+add
        return value
    }

    private fun fetchLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST)

            return
        }
        val task=fuesdLocation?.lastLocation

        task?.addOnSuccessListener(this) { location->
            if(location!=null){
                this.currentLocation=location
               val mapFragement=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragement.getMapAsync(this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
      when(requestCode){
          LOCATION_REQUEST -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              fetchLocation()
          }
      }
    }
    fun locationText(address: String){
        location_tv.text=address
    }

}