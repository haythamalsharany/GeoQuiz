package com.alsharany.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    val easyQuestion = listOf(
        Question(R.string.sanaa_question, true, false, "easy"),
        Question(R.string.taiz_question, true, false, "easy"),
        Question(R.string.ibb_question, true, false, "easy"),
        Question(R.string.aden_question, false, false, "easy"),
        Question(R.string.mareb_question, true, false, "easy"),
        Question(R.string.raima_question, true, false, "easy"),
    )
    val mediumQuetion = listOf(
        Question(R.string.hodida_question, true, false, "medium"),
        Question(R.string.mahra_question, true, false, "medium"),
        Question(R.string.lahg_question, true, false, "medium"),
        Question(R.string.mahweet_question, false, false, "medium"),

        )

    val defficlutQustion = listOf(
        Question(R.string.Pepulation_yemen, true, false, "diff"),
        Question(R.string.rusia_question, true, false, "diff"),
        Question(R.string.french_question, true, false, "diff"),
        Question(R.string.turkey_question, false, false, "diff"),
        Question(R.string.egypt_question, false, false, "diff"),
        Question(R.string.IraQ_question, true, false, "diff"),
    )

    var QuestionBank = mutableListOf<Question>()


    var currentIndex = 0
    var trueAnswer = 0
    var falseAnswer = 0
    var isCheater = false
    var score = 0
    val currentQuestionAnswer: Boolean
        get() {
            return QuestionBank[currentIndex].answer
        }
    val currentQustionText:Int
        get() {

            return QuestionBank[currentIndex].Question_text

        }
    val currentQustionStatus:Boolean
        get(){
            return QuestionBank[currentIndex].answerStatus
        }
    val currentQustionLevel:String
        get(){
            return QuestionBank[currentIndex].level
        }

    fun moveToNextQuestion(){
        currentIndex += 1
    }
    fun moveToPreviusQuestion(){
        currentIndex -= 1
    }



}
