package com.alsharany.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    val easyQuestion = listOf<Question>(
        Question(R.string.sanaa_question, true, false, "easy", 10),
        Question(R.string.taiz_question, true, false, "easy", 10),
        Question(R.string.ibb_question, true, false, "easy", 10),
        Question(R.string.aden_question, false, false, "easy", 10),
        Question(R.string.mareb_question, true, false, "easy", 10),
        Question(R.string.raima_question, true, false, "easy", 10),
    )
    val mediumQuetion = listOf<Question>(
        Question(R.string.hodida_question, true, false, "medium", 15),
        Question(R.string.mahra_question, true, false, "medium", 15),
        Question(R.string.lahg_question, false, false, "medium", 15),
        Question(R.string.mahweet_question, false, false, "medium", 15),

        )

    val defficlutQustion = listOf<Question>(
        Question(R.string.Pepulation_yemen, true, false, "diff", 25),
        Question(R.string.rusia_question, true, false, "diff", 25),
        Question(R.string.french_question, true, false, "diff", 25),
        Question(R.string.turkey_question, false, false, "diff", 25),
        Question(R.string.egypt_question, false, false, "diff", 25),
        Question(R.string.IraQ_question, true, false, "diff", 25),
    )

    var QuestionBank = mutableListOf<Question>()


    var currentIndex = 0
    var trueAnswer = 0
    var falseAnswer = 0
    var isCheater = false
    var grade = 0
    val currentQuestionAnswer: Boolean
        get() {
            return QuestionBank[currentIndex].answer
        }
    val currentQustionText:Int
        get() {

            return QuestionBank[currentIndex].Question_text


        }
    fun moveToNextQuestion(){
        currentIndex += 1
    }
    fun moveToPreviusQuestion(){
        currentIndex -= 1
    }



}
