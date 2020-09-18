package com.alsharany.geoquiz

import android.os.Bundle
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
        Question(R.string.jawf_question, false),

        )
    private var currentIndex = 0

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
//            //Toast.makeText(this,R.string.Correct_toast,Toast.LENGTH_LONG).show()
//            var  tost=  Toast.makeText(this,R.string.wrong_toast,Toast.LENGTH_LONG)
//            tost.setGravity(Gravity.TOP,0,0)
//            tost.show()
            checkAnswer(true)
        }
        false_btn.setOnClickListener {
            // Toast.makeText(this,R.string.wrong_toast,Toast.LENGTH_LONG).show()
//       var  tost=  Toast.makeText(this,R.string.wrong_toast,Toast.LENGTH_LONG)
//            tost.setGravity(Gravity.TOP,0,0)
//            tost.show()
            checkAnswer(false)

        }
        updateQuestion()
        nextBtn.setOnClickListener {
            currentIndex = (currentIndex + 1) % QuestionBank.size
            updateQuestion()

        }
        previusBtn.setOnClickListener {

            currentIndex = if (currentIndex == 0) {
                Toast.makeText(this, "therr is no previus ", Toast.LENGTH_LONG).show()
                currentIndex
            } else
                currentIndex - 1
            updateQuestion()

        }
        QuestionText.setOnClickListener {
            currentIndex = (currentIndex + 1) % QuestionBank.size

            updateQuestion()

        }

    }

    private fun updateQuestion() {

        QuestionText.setText(QuestionBank[currentIndex].Question_text)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = QuestionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer)
            R.string.Correct_toast
        else
            R.string.wrong_toast
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()
    }

}