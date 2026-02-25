package com.example.roadanomalydetector

import kotlin.math.abs

object NodeManager {

    private val reports = mutableListOf<PotholeReport>()

    private const val DISTANCE_THRESHOLD = 0.0002  // ~20 meters
    private const val CONFIRM_COUNT = 3

    fun addReport(report: PotholeReport): Boolean {
        reports.add(report)

        val nearbyCount = reports.count {
            abs(it.latitude - report.latitude) < DISTANCE_THRESHOLD &&
                    abs(it.longitude - report.longitude) < DISTANCE_THRESHOLD
        }

        return nearbyCount >= CONFIRM_COUNT
    }
}
