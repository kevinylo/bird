package bird


import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.*

class BirdControllerTest {

  private val mockBirds: ArrayList<Bird>
    get() {
      val birds = ArrayList<Bird>(10)
      //var builder: Bird.Builder = Bird.Builder()

      // Locked
      val bird1 = Bird(id = "001", distanceToCurrentLocation = 50, isLocked = true, batteryLife = 0.5f)
      birds.add(bird1)

      // Not locked but low battery
      val bird2 = Bird(id = "002", distanceToCurrentLocation = 10, isLocked = false, batteryLife = 0.1f)
      birds.add(bird2)

      // Not locked and good battery
      val bird3 = Bird(id = "003", distanceToCurrentLocation = 30, isLocked = false, batteryLife = 0.5f)
      birds.add(bird3)

      // Not locked and good battery
      val bird4 = Bird(id = "004", distanceToCurrentLocation = 31, isLocked = false, batteryLife = 0.5f)
      birds.add(bird4)

      // Not locked and good battery
      val bird5 = Bird(id = "005", distanceToCurrentLocation = 35, isLocked = false, batteryLife = 0.5f)
      birds.add(bird5)

      // Not locked and good battery
      val bird6 = Bird(id = "006", distanceToCurrentLocation = 20, isLocked = false, batteryLife = 0.5f)
      birds.add(bird6)

      // Not locked and good battery
      val bird7 = Bird(id = "007", distanceToCurrentLocation = 25, isLocked = false, batteryLife = 0.5f)
      birds.add(bird7)

      // Not locked and good battery
      val bird8 = Bird(id = "008", distanceToCurrentLocation = 100, isLocked = false, batteryLife = 0.5f)
      birds.add(bird8)

      // Not locked and good battery
      val bird9 = Bird(id = "009", distanceToCurrentLocation = 150, isLocked = false, batteryLife = 0.5f)
      birds.add(bird9)

      // Damaged
      val bird10 = Bird(id = "010", distanceToCurrentLocation = 40, isDamaged = true, batteryLife = 0.5f)
      birds.add(bird10)

      // Not locked but in motion for some reason
      val bird11 = Bird(id = "011", distanceToCurrentLocation = 5, isLocked = false, isInMotion = true, batteryLife = 0.5f)
      birds.add(bird11)

      return birds
    }

  @Before
  fun setUp() {
    BirdController.setBirds(mockBirds.toTypedArray())
  }

  @Test
  fun testGetAvailableBirds() {
    val availableBirds = BirdController.getAvailableBirds(30)
    assertThat(availableBirds.size).isEqualTo(3)
  }

  @Test
  fun testGetClosetAvailableBird() {
    val bird = BirdController.getClosestAvailableBird()
    assertThat(bird!!.id).isEqualTo("006")
  }

  @Test
  fun testSortedOrder() {
    val sortedBirds = BirdController.getSortedAvailableBirds()

    var ascending = true
    var distanceToCurrentLocation = 0
    for ((_, bird) in sortedBirds!!) {
      if (bird.distanceToCurrentLocation < distanceToCurrentLocation) {
        ascending = false
      }
      distanceToCurrentLocation = bird.distanceToCurrentLocation
    }
    assertThat(ascending).isEqualTo(true)
  }

}
