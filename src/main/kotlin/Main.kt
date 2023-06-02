import java.util.Scanner // Import the Scanner.
import java.io.File // Import file reading.
import com.google.gson.Gson // Import Gson for handling json data.
import java.util.InputMismatchException
import kotlin.random.Random // Import random number generation.

// The quote data object.
data class QuoteData(
    val quote: String?, // The quote.
    val movie: String?, // The title of the movie/show.
    val type: String?, // The type (movie, tv, anime...)
    val year: Int? // Year the title came out.
)

fun main() {
    // Define the input getter using the Scanner.
    val input = Scanner(System.`in`)

    // Get the json file.
    val jsonString = File("src/test/quotes.json").inputStream().bufferedReader().use { it.readText() }

    // Initialize lists and arrays.
    // Create QuoteData data objects from the json file.
    val quotesList = Gson().fromJson(jsonString, Array<QuoteData>::class.java)
    val movieQuoteList = mutableListOf<QuoteData>()
    val tvQuoteList = mutableListOf<QuoteData>()

    // Separate the quotes into lists of movies and tv shows.
    for (quote in quotesList) {
        if(quote.type == "movie") {
            movieQuoteList += quote
        }
        else {
            tvQuoteList += quote
        }
    }

    // Welcome message.
    println("Welcome to the quotes quiz!")
    println()
    println("Enter the type of media you want to get quotes for " +
            "and how many questions you want to get started!")
    print("Press ENTER to start: ")
    readln()
    println()

    // Prompt the user to pick between movie or t.v. quotes.
    var typeInput = 'A'
    println("Would you like to do movie quotes or television quotes?")
    while (typeInput != 'M' && typeInput != 'T') {
        print("Type M for movies or T for Television: ")
        typeInput = input.next()[0]
    }

    // Have the user pick a duration for the quiz.
    var totalQuestions = 0
    println("How many questions will be in the quiz?")
    while (totalQuestions <= 0
        || totalQuestions >= movieQuoteList.size // Used to ensure the user doesn't go over the total possible questions.
        || totalQuestions >= tvQuoteList.size) { // Same as above.
        try {
            print("Number of questions(int): ")
            totalQuestions = input.nextInt()
            if (totalQuestions >= movieQuoteList.size
                || totalQuestions >= tvQuoteList.size) {
                println("Total questions is too high!")
            }
        }
        // Handle errors if the user inputs a non-int.
        catch (exception: InputMismatchException) {
            input.next()
            println("Please enter a positive int.")
        }
    }

    // Blank line.
    println()

    val correctQuestions = runQuiz(totalQuestions, typeInput, movieQuoteList, tvQuoteList)

    // The program counts how many questions you get right, and total questions.
    // If the user gets a perfect score, print a special message.
    if (correctQuestions == totalQuestions) {
        println("Amazing! You got a perfect score!")
    }
    else {
        println("You got $correctQuestions/$totalQuestions answers right.")
    }

    // After the quiz has run, the program prints a goodbye message and ends.
    println()
    print("The quiz is over! Press ENTER to exit: ")
    readln()
}

// The function containing the quiz loop.
// Returns total correct answers as an int.
fun runQuiz(totalQuestions: Int,
            typeInput: Char,
            movieQuoteList: MutableList<QuoteData>,
            tvQuoteList: MutableList<QuoteData>): Int {

    // Initialize correct questions amount.
    var answeredQuestions = 0
    var correctQuestions = 0

    // Initialize list of used quotes.
    val usedQuotes = mutableListOf<QuoteData>()

    // Start the quiz, and continue until time runs out.
    while(answeredQuestions != totalQuestions) {

        // Get a random quote.
        val quote:QuoteData = if (typeInput == 'M') {
            movieQuoteList[Random.nextInt(0, movieQuoteList.size)]
        } else {
            tvQuoteList[Random.nextInt(0, tvQuoteList.size)]
        }

        // Prevent questions from being reused.
        if (usedQuotes.contains(quote)) {
            continue
        }

        // Increment total questions.
        answeredQuestions++

        // Print a random quote.
        println("Quote: ${quote.quote}")

        // Get the user's guess.
        print("What is this quote from? ")
        val guess = readln()

        // Check if the input is correct.
        // Output a message indicating if the answer was correct or incorrect.
        if (guess.equals(quote.movie, ignoreCase=true)) {
            println("Correct!")
            correctQuestions++
        }
        else {
            println("Incorrect. Correct answer: ${quote.movie}")
        }
        usedQuotes += quote
        // Blank line.
        println()
    }
    return correctQuestions
}