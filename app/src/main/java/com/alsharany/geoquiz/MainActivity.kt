package com.alsharany.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_CODE_CHEAT = 0
class MainActivity : AppCompatActivity() {
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }


    private lateinit var QuestionText: TextView
    private lateinit var nextBtn: ImageButton
    private lateinit var previusBtn: ImageButton
    private lateinit var cheatButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.nextBtn = findViewById(R.id.next_btn)
        previusBtn = findViewById(R.id.previus_btn)
        QuestionText = findViewById(R.id.Question_text)
        cheatButton = findViewById(R.id.cheat_button)
        updateQuestion()
        true_btn.setOnClickListener {
            checkAnswer(true)
            changeAnswerStatus()
            btnEnableUnEbale(false_btn,false)
            btnEnableUnEbale(true_btn,false)

            if  (checkCountOfAnswers())
                getGrade()
        }
        false_btn.setOnClickListener {
            checkAnswer(false)
            changeAnswerStatus()
            btnEnableUnEbale(false_btn,false)
            btnEnableUnEbale(true_btn,false)
            if (checkCountOfAnswers())
                getGrade()


        }
        cheatButton.setOnClickListener {
            // val intent = Intent(this, CheatingActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatingActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)

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
            checkIndex(nextBtn.id)
            updateQuestion()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    private fun updateQuestion() {
        //enable true button false button
        if(quizViewModel.QuestionBank[quizViewModel.currentIndex].answerStatus==false) {
            btnEnableUnEbale(true_btn, true)
            btnEnableUnEbale(false_btn, true)
        }
        else
            msg_Toast("this question is alrady answered",Gravity.CENTER_VERTICAL)



        QuestionText.setText(quizViewModel.currentQustionText)


    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.QuestionBank[quizViewModel.currentIndex].answer
//        val messageResId = if (userAnswer == correctAnswer) {
//            trueAnswer++
//            R.string.Correct_toast
//        } else {
//            falseAnswer++
//            R.string.wrong_toast

//        }

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> {
                quizViewModel.trueAnswer++
                R.string.Correct_toast}
            else -> { quizViewModel.falseAnswer++
                R.string.wrong_toast
            }
        }
        var toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()

    }

    private fun checkCountOfAnswers(): Boolean {
        var checker =if(quizViewModel.currentIndex == quizViewModel.QuestionBank.size - 1&& quizViewModel.trueAnswer + quizViewModel.falseAnswer == quizViewModel.QuestionBank.size  )
                  true
        else false
        return checker

    }

    private fun getGrade() {
        var grade = quizViewModel.trueAnswer * 10
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
                btnEnableUnEbale(nextBtn,true)
                if (quizViewModel.currentIndex <= 1) {

                    quizViewModel.currentIndex=0

                    btnEnableUnEbale(previusBtn,false)

                } else {
                    btnEnableUnEbale(previusBtn, true)
                    quizViewModel.moveToPreviusQuestion()
                }

            }
            nextBtn.id -> {
                btnEnableUnEbale(previusBtn,true)
                if (quizViewModel.currentIndex == quizViewModel.QuestionBank.size - 1) {
                    btnEnableUnEbale(nextBtn,false)

                    quizViewModel.currentIndex
                } else {
                    btnEnableUnEbale(nextBtn,true)

                    quizViewModel.moveToNextQuestion()
                }

            }
        }
    }

    private fun btnEnableUnEbale(view:View,BState: Boolean) {
        view.isEnabled = BState

    }
    private  fun  changeAnswerStatus(){
        quizViewModel.QuestionBank[quizViewModel.currentIndex].answerStatus=true
    }

}