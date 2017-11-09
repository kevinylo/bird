package bird


object LocationHelper {

    fun getMeterDistanceBetweenLocation(location1: Location, location2: Location): Int {
        val pk = (180f / Math.PI).toFloat()

        val a1 = location1.latitude / pk
        val a2 = location1.longitude / pk
        val b1 = location2.latitude / pk
        val b2 = location2.longitude / pk

        val t1 = Math.cos(a1.toDouble()) * Math.cos(a2.toDouble()) * Math.cos(b1.toDouble()) * Math.cos(b2.toDouble())
        val t2 = Math.cos(a1.toDouble()) * Math.sin(a2.toDouble()) * Math.cos(b1.toDouble()) * Math.sin(b2.toDouble())
        val t3 = Math.sin(a1.toDouble()) * Math.sin(b1.toDouble())
        val tt = Math.acos(t1 + t2 + t3)

        return (6366000 * tt).toInt()
    }
}
