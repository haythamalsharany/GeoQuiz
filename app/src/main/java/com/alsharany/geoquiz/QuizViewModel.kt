package com.alsharany.geoquiz

import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    val QuestionBank = listOf<Question>(
        Question(R.string.sanaa_question, true,false),
        Question(R.string.taiz_question, true,false),
        Question(R.string.ibb_question, true,false),
        Question(R.string.aden_question, false,false),
        Question(R.string.mareb_question, true,false),
        Question(R.string.raima_question, true,false),
        Question(R.string.hodida_question, true,false),
        Question(R.string.mahra_question, true,false),
        Question(R.string.lahg_question, false,false),
        Question(R.string.mahweet_question, false,false),

        )
    var currentIndex = 0
    var trueAnswer = 0
    var falseAnswer = 0
    var isCheater = false
    val currentQuestionAnswer:Boolean
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