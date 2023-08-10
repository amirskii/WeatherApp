package com.example.weatherapp.domain.location

import com.example.weatherapp.domain.models.MapLocation
import kotlinx.coroutines.flow.Flow

interface LocationTracker {
    fun getCurrentLocation(): Flow<MapLocation>
}