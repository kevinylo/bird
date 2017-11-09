package bird


import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.SortedSet
import java.util.TreeSet

object BirdController : BirdOperations {

    // all the birds in the system
    private var allBirdsList: List<Bird>? = null

    // last updated location
    private var location: Location? = null

    // map from bird Id to bird model
    private var idToBirdMap: MutableMap<String, Bird>? = null

    // sorted based on relative distance from current location
    private var sortedBirdSet: SortedSet<Map.Entry<String, Bird>>? = null

    override fun setBirds(birds: Array<Bird>) {
        this.allBirdsList = Arrays.asList(*birds)
        refreshBirds(allBirdsList, location)
    }

    override fun setCurrentLocation(location: Location) {
        this.location = location
    }

    override fun getAvailableBirds(radius: Int): List<Bird> {
        val availableBirdWithinRadius = ArrayList<Bird>()

        // Find, within sorted set, the birds within radius
        for (entry in sortedBirdSet!!) {
            val currentBird = entry.value
            if (currentBird.distanceToCurrentLocation <= radius) {
                availableBirdWithinRadius.add(currentBird)
            }
        }

        return availableBirdWithinRadius
    }

    override fun getClosestAvailableBird(): Bird?  {
        if (sortedBirdSet == null) return null
        return sortedBirdSet!!.first().value
    }

    override fun getSortedAvailableBirds(): SortedSet<Map.Entry<String, Bird>>? {
        return sortedBirdSet
    }

    override fun getBird(id: String): Bird? {
        return if (idToBirdMap!!.containsKey(id)) {
            idToBirdMap!![id]
        } else {
            null
        }
    }

    // Periodically or per situation called to get current available birds
    private fun refreshBirds(allBirdsList: List<Bird>?, location: Location?) {
        if (allBirdsList == null) return

        idToBirdMap = HashMap()
        sortedBirdSet = Collections.synchronizedSortedSet<Map.Entry<String, Bird>>(TreeSet<Map.Entry<String, Bird>>(CurrentLocationToBirdComparator()))

        // filter out birds that are currently in use or non-operational (battery critical, damaged, etc)
        for (bird in allBirdsList) {
            if (bird.isAvailable) {
                // create the map between id to bird model
                idToBirdMap!!.put(bird.id, bird)

                if (location != null) {
                    // calculate the relative distance between current location and bird
                    bird.distanceToCurrentLocation = LocationHelper.getMeterDistanceBetweenLocation(location, bird.location!!)
                }
            }
        }

        // add the map into tree set to be sorted
        sortedBirdSet!!.addAll(idToBirdMap!!.entries)
    }

    private class CurrentLocationToBirdComparator : Comparator<Map.Entry<String, Bird>> {

        override fun compare(map1: Map.Entry<String, Bird>, map2: Map.Entry<String, Bird>): Int {
            val distance1 = map1.value.distanceToCurrentLocation
            val distance2 = map2.value.distanceToCurrentLocation

            return if (distance1 > distance2) {
                1
            } else if (distance1 < distance2) {
                -1
            } else {
                0
            }
        }
    }

}
