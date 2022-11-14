import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    // Create a tally that counts each character in alphabet
    var charTally = AlphabetMap().alphabetMap

    // Import Wordle word list from text file
    val wordArray: Array<String> = arrayOf()
    val list: MutableList<String> = wordArray.toMutableList()
    readFileAsLinesUsingBufferedReader("sample-word-list.txt", list)

    // Select word from imported list
    val wordListSize: Int = list.size
    val randNum: Double = Math.random()
    val selectedIndex: Int = (randNum * wordListSize).toInt() // toInt() rounds down
    val selectedWord: String = list[selectedIndex]
    val selectedWordLength: Int = selectedWord.length

    // convert to a char array
    val selectedWordCharArray = selectedWord.toCharArray()


    println("Welcome to Bwordle!!!")
    println("Correct letter in the correct place; app returns 'g' for green.")
    println("Correct letter, wrong place; app returns 'o' for orange.")
    println("Letter is not used in word; app returns 'X'")
    println("Computer has selected a $selectedWordLength letter word.")
    println("Please try to guess it by entering a word the same length!")

    var guessCounter: Int = 1
    var input: String

    // Game process
    while (guessCounter <= 6) {
        // Set the Character Tally map to initial values for the selected word.
        selectedWordCharArray.forEach { c: Char -> setAlphabetTally(charTally, c) }

        // USER GUESS VALIDATION
        // must only contain letters of alphabet and be five charaters long.
        do {
            val wordPattern = Regex("[A-Za-z]{5}")       //  "\\w+"
            println("What is your guess: ")
            val scanner  = Scanner(System.`in`)
            input = scanner.next().trim()
            val isValidGuess: Boolean = input.matches(wordPattern)
            if (!isValidGuess) println("Invalid guess!\n" +
                    "Please try again! Remember, only use the alphabet and the word is 5 characters")
        } while (!isValidGuess);

        println("Valid guess!");
        val inputLowercase = input.lowercase()
        val inputAsCharArray = inputLowercase.toCharArray()

        val wordAsCharArray = selectedWord.toCharArray()

        var resultOfGuess: CharArray = checkForAlignedChars(wordAsCharArray, inputAsCharArray, charTally)

        resultOfGuess = checkForCharInWord(wordAsCharArray, inputAsCharArray, charTally, resultOfGuess)
        println("Wordle result for guess #$guessCounter : ")
        for (c in resultOfGuess) {
            print("$c ")
        }
        println()

        guessCounter++

        val convertedStringResultOfGuess: String = String(resultOfGuess)
        if (convertedStringResultOfGuess == "ggggg") {
            println("CONGRATULATIONS!!!\nCorrect guess!")
            break
        }
    }

    // After 6 guesses or correct guess, diplay the correct answer.
    println("Word was: ")
    for (c in selectedWordCharArray) {
        print("$c ")
    }
    println()
    println("End of game.\nPlease try again by running program again!")
}

fun setAlphabetTally(alphabetTally:  MutableMap<Char, Int>, char: Char) {
    var alphabetValue: Int? = alphabetTally[char]
    if (alphabetValue != null) {
        alphabetTally[char] = alphabetValue + 1
    }
}

fun checkForCharInWord(targetWord: CharArray,
                       userGuess: CharArray,
                       alphabetTally:  MutableMap<Char, Int>,
                       result: CharArray): CharArray {
    for (i in 0..userGuess.size -1) {
        var alphabetValue: Int? = alphabetTally[userGuess[i]]

        if (alphabetValue != null &&
            alphabetValue > 0) {
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