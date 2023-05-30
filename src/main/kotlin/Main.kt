import java.util.Scanner // Import the Scanner.
import java.io.File // Import file reading.

fun main() {
    // Define the input getter using the Scanner.
    val input = Scanner(System.`in`)

    // Print out all movie and tv quote data to the terminal.
    val fileName = "src/test/quotes.json" // json file with all the quote data.
    val lines:List<String> = File(fileName).readLines()
    lines.forEach {line -> println(line)
    }

    // Welcome message.
    println("Welcome to the quotes quiz!")
    println()

    // Prompt the user to pick between movie or t.v. quotes.
    print("Would you like to do movie quotes or television quotes? Type M for movies or T for Television: ")
    val typeInput: Char = input.next()[0]

    // Have the user pick a duration for the quiz.
    print("How long will the quiz be in seconds? ")
    val quizDuration = input.nextInt()

    // Have the user pick a time limit for each question.
    print("How much time will be given for each question in seconds? ")
    val questionDuration = input.nextInt()

    // Start the quiz, and continue until time runs out.

    // The quiz displays a quote, and the user must guess who the quote was said by.
    // You only get one guess for each quote.
    // The program counts how many questions you get right, and total questions.

    // After the quiz has run, the program prints a goodbye message and ends.
    println()
    print("The quiz is over!")
}