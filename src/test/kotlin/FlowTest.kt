import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


fun generateValue(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(1)
        emit(i)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class FlowTest() {
    @Test
    fun testFlowBlocking(): Unit = runTest {
        val result = mutableListOf<Int>()

        generateValue().collect {
            result.add(it)
        }

        generateValue().collect {
            result.add(it)
        }

        assertEquals(mutableListOf(1, 2, 3, 1, 2, 3), result)
    }

    @Test
    fun testFlow(): Unit = runTest {
        val result = mutableListOf<Int>()

        launch {
            launch {
                generateValue().collect {
                    result.add(it)
                    delay(1)
                }
            }
            launch {
                generateValue().collect {
                    result.add(it)
                    delay(2)
                }
            }
        }.join()

        assertEquals(mutableListOf(1, 1, 2, 2, 3, 3), result)
    }

    @Test
    fun testChannel() = runTest {
        val channel = Channel<Int>()

        launch {
            generateValue().collect {
                channel.send(it)
            }

            channel.close()
        }

        launch {
            assertEquals(mutableListOf(1, 2, 3), channel.consumeAsFlow().toList())
        }
    }

    @Test
    fun testConcat() = runTest {
        val result = mutableListOf<Int>()

        flowOf(generateValue(), generateValue()).flattenConcat().collect {
            result.add(it)
        }

        assertEquals(mutableListOf(1, 2, 3, 1, 2, 3), result)
    }

    @Test
    fun testMerge() = runTest {
        val result = mutableListOf<Int>()

        flowOf(generateValue(), generateValue()).flattenMerge().collect {
            result.add(it)
        }

        assertEquals(mutableListOf(1, 1, 2, 2, 3, 3), result)
    }
}