package bird

data class Bird(
        var id: String = "",
        var firmwareVersion: String? = null,
        var location: Location? = null,

        var batteryLife: Float = 0.toFloat(),
        var travelTimeMinutes: Long = 0,
        var idleTime: Long = 0,
        var distanceTraveled: Long = 0,
        var numberOfRides: Int = 0,
        var distanceToCurrentLocation: Int = 0,

        // status
        var isLocked: Boolean = false,
        var isDamaged: Boolean = false,
        var isInMotion: Boolean = false,
        var isBatteryCharging: Boolean = false,
        var isBatteryCritical: Boolean = batteryLife < BATTERY_CRITICAL_LEVEL,
        val isAvailable: Boolean = !isLocked && !isBatteryCritical && !isDamaged && !isInMotion && !isBatteryCharging) {

    companion object {

        private val BATTERY_CRITICAL_LEVEL = 0.15f
    }
}

