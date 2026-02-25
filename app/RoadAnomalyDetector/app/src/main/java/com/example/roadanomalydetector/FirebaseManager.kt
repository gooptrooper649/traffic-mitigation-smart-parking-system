package com.example.roadanomalydetector

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import android.net.Uri

object FirebaseManager {

    private val database = FirebaseDatabase.getInstance(
        "https://roadanomalydetector-66cb6-default-rtdb.asia-southeast1.firebasedatabase.app"
    )

    private val detectionRef = database.getReference("detections")
    private val manualRef = database.getReference("manualReports")

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference.child("pothole_images")

    fun uploadDetection(
        lat: Double,
        lon: Double,
        magnitude: Double,
        deviceId: String
    ) {

        val data = hashMapOf(
            "lat" to lat,
            "lon" to lon,
            "magnitude" to magnitude,
            "timestamp" to System.currentTimeMillis(),
            "deviceId" to deviceId
        )

        detectionRef.push().setValue(data)

        checkForConfirmation(lat, lon)
    }

    fun uploadManualReport(

        imageUri: Uri,
        lat: Double,
        lon: Double,
        deviceId: String
    ) {

        // IMPORTANT: create imageRef inside function
        val imageRef = storageRef.child("${System.currentTimeMillis()}.jpg")

        imageRef.putFile(imageUri)
            .addOnSuccessListener {

                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->

                    val data = hashMapOf(
                        "lat" to lat,
                        "lon" to lon,
                        "imageUrl" to downloadUrl.toString(),
                        "timestamp" to System.currentTimeMillis(),
                        "deviceId" to deviceId
                    )

                    manualRef.push().setValue(data)
                }
            }
    }

    private fun checkForConfirmation(
        lat: Double,
        lon: Double
    ) {

        val currentTime = System.currentTimeMillis()
            .addOnSuccessListener {
                Log.d("UPLOAD", "Image uploaded successfully")
            }
            .addOnFailureListener {
                Log.e("UPLOAD", "Upload failed: ${it.message}")
            }
            .addOnFailureListener { e ->
                Log.e("FIREBASE_UPLOAD", "Upload failed", e)
            }



        detectionRef.get().addOnSuccessListener { snapshot ->

            val nearbyDevices = mutableSetOf<String>()

            for (child in snapshot.children) {

                val dLat = child.child("lat").getValue(Double::class.java) ?: continue
                val dLon = child.child("lon").getValue(Double::class.java) ?: continue
                val dTime = child.child("timestamp").getValue(Long::class.java) ?: continue
                val dDevice = child.child("deviceId").getValue(String::class.java) ?: continue

                val distance = distanceInMeters(lat, lon, dLat, dLon)

                if (distance < 20 && currentTime - dTime < 30000) {
                    nearbyDevices.add(dDevice)
                }
            }

            if (nearbyDevices.size >= 1) {

                val confirmRef = database.getReference("confirmedPotholes")

                confirmRef.push().setValue(
                    mapOf(
                        "lat" to lat,
                        "lon" to lon,
                        "confirmedAt" to currentTime
                    )
                )
            }
        }
    }

    private fun distanceInMeters(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {

        val earthRadius = 6371000.0

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) *
                Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c
    }
}
