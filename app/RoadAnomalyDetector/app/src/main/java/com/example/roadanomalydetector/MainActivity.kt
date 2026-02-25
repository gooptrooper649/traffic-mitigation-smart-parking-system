package com.example.roadanomalydetector


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.*
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlin.math.sqrt
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import java.io.File
import androidx.core.content.FileProvider
import com.example.roadanomalydetector.FirebaseManager







class MainActivity : AppCompatActivity(), SensorEventListener {
    private var selectedImageUri: Uri? = null
    private var currentLat: Double = 0.0
    private var currentLon: Double = 0.0


    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private lateinit var tvValues: TextView
    private lateinit var tvDetection: TextView
    private lateinit var map: MapView
    private lateinit var deviceId: String
    // image




    private var imageUri: Uri? = null

    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null

    private var lastDetectionTime = 0L

    //image upload
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && imageUri != null) {
                uploadManual(imageUri!!)
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadManual(it)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this,
            getSharedPreferences("osmdroid", MODE_PRIVATE))

        setContentView(R.layout.activity_main)

        tvValues = findViewById(R.id.tvValues)
        tvDetection = findViewById(R.id.tvDetection)
        map = findViewById(R.id.map)

        map.setMultiTouchControls(true)
        map.controller.setZoom(18.0)

        //firebase upload
        deviceId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )


        //image
        val btnManual = findViewById<Button>(R.id.btnUpload)


        btnManual.setOnClickListener {
            showImagePickerOptions()
        }







        // SENSOR SETUP
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // LOCATION SETUP
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        requestLocation()
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also {
            sensorManager.registerListener(this, it,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        map.onPause()
    }

    // ==============================
    // ACCELEROMETER LOGIC
    // ==============================
    override fun onSensorChanged(event: SensorEvent?) {

        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val gravity = 9.8
            val adjustedZ = z - gravity

            val magnitude = Math.sqrt((x*x + y*y + adjustedZ*adjustedZ).toDouble())

            tvValues.text ="""
              X: ${"%.2f".format(x)}
              Y: ${"%.2f".format(y)}
              Z: ${"%.2f".format(z)}
              Magnitude: ${"%.2f".format(magnitude)}
            """.trimIndent()



            val currentTime = System.currentTimeMillis()

            if (magnitude >= 18 && currentTime - lastDetectionTime > 2000) {

                lastDetectionTime = currentTime



                tvDetection.text = "Road Status: POTHOLE DETECTED"
                tvDetection.setTextColor(resources.getColor(android.R.color.holo_blue_dark))

                addBlueMarker()

                // Clear after 3 seconds
                tvDetection.postDelayed({
                    tvDetection.text = "Road Status: Normal"
                    tvDetection.setTextColor(resources.getColor(android.R.color.black))
                }, 2000)

                addBlueMarker()
                if (currentLocation != null) {
                    FirebaseManager.uploadDetection(
                        currentLocation!!.latitude,
                        currentLocation!!.longitude,
                        magnitude,
                        deviceId
                    )
                }

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    // ==============================
    // LOCATION
    // ==============================
    private fun requestLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                currentLocation = location
                val geoPoint = GeoPoint(location.latitude, location.longitude)
                map.controller.setCenter(geoPoint)
            }
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000L,
            1f,
            locationListener,
            mainLooper
        )

        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            1000L,
            1f,
            locationListener,
            mainLooper
        )
    }



    // ==============================
    // BLUE MARKER
    // ==============================
    private fun addBlueMarker() {

        val location = currentLocation ?: return

        val geoPoint = GeoPoint(location.latitude, location.longitude)

        val marker = Marker(map)
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Detected Pothole"

        // Use default marker and tint it blue
        val drawable = resources.getDrawable(org.osmdroid.library.R.drawable.marker_default)
        drawable.setTint(android.graphics.Color.BLUE)
        marker.icon = drawable

        map.overlays.add(marker)
        map.invalidate()
    }


    ///red marker ///
    private fun addRedMarker(lat: Double, lon: Double) {

        val geoPoint = GeoPoint(lat, lon)

        val marker = Marker(map)
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Manual Reported Pothole"

        val drawable = resources.getDrawable(org.osmdroid.library.R.drawable.marker_default)
        drawable.setTint(android.graphics.Color.RED)
        marker.icon = drawable

        map.overlays.add(marker)
        map.invalidate()
    }
// images
private fun showImagePickerOptions() {

    val options = arrayOf("Camera", "Gallery")

    AlertDialog.Builder(this)
        .setTitle("Select Image")
        .setItems(options) { _, which ->

            when (which) {
                0 -> openCamera()
                1 -> galleryLauncher.launch("image/*")
            }
        }
        .show()
}

    private fun openCamera() {

        val imageFile = File.createTempFile(
            "pothole_",
            ".jpg",
            cacheDir
        )

        imageUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            imageFile
        )

        cameraLauncher.launch(imageUri)
    }


    // manual upload
    private fun uploadManual(uri: Uri) {

        val location = currentLocation ?: return

        FirebaseManager.uploadManualReport(
            uri,
            location.latitude,
            location.longitude,
            deviceId
        )

        addRedMarker(location.latitude, location.longitude)
    }



}
