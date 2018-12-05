import org.junit.Test
import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by Vincent on 11/27/18.
 * @author Vincent
 * @since 1.0, 11/27/18
 */
class TestDate {

    @Test
    fun testDate() {
        val now = LocalDateTime.now()

        val duration = Duration.between(now, now.minusSeconds(2))

        println(duration.toNanos())
        println(duration.toMillis())
        println(duration.toMinutes())
        println(duration.toHours())
        println(duration.toDays())

    }

    @Test
    fun testN() {
        val minNewCapacity: Int = 8
        val threshold = 4
        val maxCapacity = 16

        var newCapacity = minNewCapacity / threshold * threshold
        println(newCapacity)
        if (newCapacity > maxCapacity - threshold) {
            newCapacity = maxCapacity
        } else {
            newCapacity += threshold
        }
        println(newCapacity)
    }
}