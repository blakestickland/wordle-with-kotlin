import java.io.File
import java.util.*
import kotlin.math.roundToInt

fun main() {

    val wordArray: Array<String> = arrayOf<String>()
    val list: MutableList<String> = wordArray.toMutableList()

    readFileAsLinesUsingBufferedReader("sample-word-list.txt", list)

    val wordListSize: Int = list.size

    val randNum: Double = java.lang.Math.random()

    val selectedIndex: Int = (randNum * wordListSize).toInt()       // toInt() rounds down to Int

    //  pick a random word from list
    val selectedWord: String = list[selectedIndex]

    val selectedWordLength: Int = selectedWord.length

    println(selectedIndex)
    println(selectedWord)
    println(selectedWordLength)
}

fun readFileAsLinesUsingBufferedReader(fileName: String, list: MutableList<String>)
        = File(fileName).forEachLine { value ->
    run {
        val preppedValue = value.trim().split("\"", ",")
        list.add(preppedValue[1])
    }
}