import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    var alphabetMap = mutableMapOf(
        'a' to 0,
        'b' to 0,
        'c' to 0,
        'd' to 0,
        'e' to 0,
        'f' to 0,
        'g' to 0,
        'h' to 0,
        'i' to 0,
        'j' to 0,
        'k' to 0,
        'l' to 0,
        'm' to 0,
        'n' to 0,
        'o' to 0,
        'p' to 0,
        'q' to 0,
        'r' to 0,
        's' to 0,
        't' to 0,
        'u' to 0,
        'v' to 0,
        'w' to 0,
        'x' to 0,
        'y' to 0,
        'z' to 0
    )
//    var alphabetValue = alphabetMap.get('a')
//    if (alphabetValue != null) {
//        alphabetMap.put('a', alphabetValue + 1)
//    }
//
//    println(alphabetMap['a'])

    fun setAlphabetTally(alphabetTally:  MutableMap<Char, Int>, char: Char) {
        var alphabetValue: Int? = alphabetMap[char]
        if (alphabetValue != null) {
            alphabetTally[char] = alphabetValue + 1
        }
    }



    val wordArray: Array<String> = arrayOf()
    val list: MutableList<String> = wordArray.toMutableList()

    readFileAsLinesUsingBufferedReader("sample-word-list.txt", list)

    val wordListSize: Int = list.size

    val randNum: Double = Math.random()

    val selectedIndex: Int = (randNum * wordListSize).toInt()       // toInt() rounds down to Int

    //  pick a random word from list
    val selectedWord: String = list[selectedIndex]
    val selectedWordLength: Int = selectedWord.length

    // convert to a char array and add each char to alphabet tally
    val selectedWordCharArray = selectedWord.toCharArray()
    selectedWordCharArray.forEach { c: Char -> setAlphabetTally(alphabetMap, c) }

    println(selectedWord)
    println()

    println("Welcome to Bwordle!!!")
    println("Correct letter in the correct place; app returns 'g' for green.")
    println("Correct letter, wrong place; app returns 'o' for orange.")
    println("Letter is not used in word; app returns 'X'")
    println("Computer has selected a $selectedWordLength letter word.")
    println("Please try to guess it by entering a word the same length!")

    var isCorrect: Boolean
    val guessCount: Int = 0
    val scanner  = Scanner(System.`in`)
    var input: String
    do {
        val wordPattern = Regex("[A-Za-z]{5}")       //  "\\w+"
        println("What is your guess: ")
        input = scanner.next().trim()
        isCorrect = input.matches(wordPattern)
        if (!isCorrect) println("Invalid data!\nPlease guess again!")
    } while (!isCorrect);
    println("Valid data");
    var inputLowercase = input.lowercase()

    var inputAsCharArray = inputLowercase.toCharArray()
    for (c in inputAsCharArray) {
        print("$c ")
    }
    println()

    var wordAsCharArray = selectedWord.toCharArray()

    var resultOfGuess: CharArray = checkForAlignedChars(wordAsCharArray, inputAsCharArray, alphabetMap)
    for (c in resultOfGuess) {
        print("$c ")
    }
    println(alphabetMap)
    resultOfGuess = checkForCharInWord(wordAsCharArray, inputAsCharArray, alphabetMap, resultOfGuess)
    for (c in resultOfGuess) {
        print("$c ")
    }
}

fun checkForCharInWord(targetWord: CharArray,
                       userGuess: CharArray,
                       alphabetTally:  MutableMap<Char, Int>,
                       result: CharArray): CharArray {
    for (i in 0..userGuess.size -1) {
        val targetWordNot = targetWord[i]
        if (result[i] == 'g') {
            println("Index $i is GREEN, continue; $targetWordNot")
            continue
        }

        var alphabetValue: Int? = alphabetTally[userGuess[i]]

        if (alphabetValue != null &&
            alphabetValue > 0) {
            println(alphabetValue)
            for (j in 0..targetWord.size -1) {
                if (userGuess[i] == targetWord[j] && result[i] != 'g') {
                    result[i] = 'o'
                    checkForChar(alphabetTally, userGuess[i])
                }
            }
        }
    }
    return result
}

fun checkForAlignedChars (targetWord: CharArray, userGuess: CharArray, alphabetTally:  MutableMap<Char, Int>): CharArray {
    var result: CharArray = CharArray(targetWord.size)
    for (i in 0..targetWord.size -1) {
        if (targetWord[i] == userGuess[i]) {
            result[i] = 'g'
            checkForChar(alphabetTally, userGuess[i])
        }
    }
    return result
}

fun checkForChar(alphabetTally:  MutableMap<Char, Int>, char: Char) {
    var alphabetValue: Int? = alphabetTally[char]
    if ((alphabetValue != null) && (alphabetValue > 0)) {
        alphabetTally[char] = alphabetValue - 1
    }
}

fun readFileAsLinesUsingBufferedReader(fileName: String, list: MutableList<String>)
        = File(fileName).forEachLine { value ->
    run {
        val preppedValue = value.trim().split("\"", ",")
        list.add(preppedValue[1])
    }
}