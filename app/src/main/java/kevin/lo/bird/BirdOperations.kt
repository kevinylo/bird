package bird

import java.util.SortedSet


interface BirdOperations {
    fun setBirds(birds: Array<Bird>)
    fun setCurrentLocation(location: Location)
    fun getSortedAvailableBirds(): SortedSet<Map.Entry<String, Bird>>?
    fun getAvailableBirds(radius: Int): List<Bird>
    fun getBird(id: String): Bird?
    fun getClosestAvailableBird(): Bird?
}
