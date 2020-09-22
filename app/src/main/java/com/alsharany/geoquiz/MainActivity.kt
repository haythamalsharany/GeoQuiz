package com.alsharany.geoquiz

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val QuestionBank = listOf<Question>(
        Question(R.string.sanaa_question, true),
        Question(R.string.taiz_question, true),
        Question(R.string.ibb_question, true),
        Question(R.string.aden_question, false),
        Question(R.string.mareb_question, true),
        Question(R.string.raima_question, true),
        Question(R.string.hodida_question, true),
        Question(R.string.mahra_question, true),
        Question(R.string.lahg_question, false),
        Question(R.string.mahweet_question, false),

        )
    private var currentIndex = 0
    private var trueAnswer = 0
    private var falseAnswer = 0

    private lateinit var QuestionText: TextView
    private lateinit var nextBtn: ImageButton
    private lateinit var previusBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nextBtn = findViewById(R.id.next_btn)
        previusBtn = findViewById(R.id.previus_btn)
        QuestionText = findViewById(R.id.Question_text)
        updateQuestion()
        true_btn.setOnClickListener {
            checkAnswer(true)
            if (currentIndex == QuestionBank.size - 1)
                getGrade()
        }
        false_btn.setOnClickListener {
            // Toast.makeText(this,R.string.wrong_toast,Toast.LENGTH_LONG).show()
//       var  tost=  Toast.makeText(this,R.string.wrong_toast,Toast.LENGTH_LONG)
//            tost.setGravity(Gravity.TOP,0,0)
//            tost.show()
            checkAnswer(false)
            if (currentIndex == QuestionBank.size - 1)
                getGrade()


        }
        updateQuestion()
        nextBtn.setOnClickListener {
            // currentIndex = (currentIndex + 1) % QuestionBank.size
            checkIndex(nextBtn.id)
            updateQuestion()
        }
        previusBtn.setOnClickListener {
            checkIndex(previusBtn.id)
            updateQuestion()
        }
        QuestionText.setOnClickListener {
            currentIndex = (currentIndex + 1) % QuestionBank.size
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        //enable true button false button
        btnEnableUnEbale(true)
        QuestionText.setText(QuestionBank[currentIndex].Question_text)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = QuestionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            trueAnswer++
            R.string.Correct_toast
        } else {
            falseAnswer++
            R.string.wrong_toast
        }
        var toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
        btnEnableUnEbale(false)
    }

    private fun getGrade() {
        var grade=trueAnswer * 10
        msg_Toast("your score is : ${grade}%", Gravity.CENTER_VERTICAL)
    }

    private fun msg_Toast(msg: CharSequence, gravity: Int) {
        // Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
        var toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.setGravity(gravity, 0, 0)
        toast.show()
    }

    private fun checkIndex(viewId: Int) {
        when (viewId) {
            previusBtn.id -> {
                currentIndex = if (currentIndex == 0) {
                    msg_Toast("thetre is the frist Question ", Gravity.BOTTOM)
                    currentIndex
                } else
                    currentIndex - 1
            }
            nextBtn.id -> {
                currentIndex = if (currentIndex == QuestionBank.size - 1) {
                    msg_Toast("there is the last Question ", Gravity.BOTTOM)
                    currentIndex
                } else
                    currentIndex + 1
            }
        }
    }

    private fun btnEnableUnEbale(BState: Boolean) {
        false_btn.isEnabled = BState
        true_btn.isEnabled = BState
    }

}