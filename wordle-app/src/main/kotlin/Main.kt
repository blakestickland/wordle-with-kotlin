import java.io.File
import java.util.*
import kotlin.math.roundToInt

fun main() {

    val wordArray: Array<String> = arrayOf<String>()
    val list: MutableList<String> = wordArray.toMutableList()

    readFileAsLinesUsingBufferedReader("sample-word-list.txt", list)

}

fun readFileAsLinesUsingBufferedReader(fileName: String, list: MutableList<String>)
        = File(fileName).forEachLine { value ->
    run {
        val preppedValue = value.trim().split("\"", ",")
        list.add(preppedValue[1])
    }
}