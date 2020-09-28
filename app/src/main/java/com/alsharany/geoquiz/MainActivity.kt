package com.alsharany.geoquiz

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
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
    private lateinit var playerScoreTextview: TextView
    private lateinit var nextBtn: ImageButton
    private lateinit var previusBtn: ImageButton
    private lateinit var cheatButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        //sounds file  declare and intial   ------------------------------------------------------------------

        var falseSound = MediaPlayer.create(this, R.raw.s1)
        var trueSound = MediaPlayer.create(this, R.raw.s2)

        //copy from QuizViewModel lists to temp new lists  --------------------------------------------------

        val easylist = arrayListOf<Question>()
        easylist.apply {
            addAll(quizViewModel.easyQuestion)
        }
        val meduimlist = arrayListOf<Question>().apply {
            addAll(quizViewModel.mediumQuetion)
        }
        val diffcalutList = arrayListOf<Question>().apply {
            addAll(quizViewModel.defficlutQustion)
        }
        // call the randomListQustion  to get main list that will desplay in activity  -----------------------------------

        randomListOfQustion(easylist)
        randomListOfQustion(meduimlist)
        randomListOfQustion(diffcalutList)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // import the views from xml file -----------------------------------------------------------------------------------

        nextBtn = findViewById(R.id.next_btn)
        previusBtn = findViewById(R.id.previus_btn)
        QuestionText = findViewById(R.id.Question_text)
        playerScoreTextview = findViewById(R.id.player_Score)
        cheatButton = findViewById(R.id.cheat_button)

        // call updateQuestion function to put an defualt text in the QuestionText ----------------------

        updateQuestion()
        //   adding click event to true button--------------------------------------------------------------

        true_btn.setOnClickListener {
            checkAnswer(true)
            it.let {
                if (quizViewModel.currentQuestionAnswer == true) {
                    changeBTNBackgraund(it, R.color.trueAnswer)
                    trueSound.start()
                    getGrade(true)
                    showScore()
                } else {
                    changeBTNBackgraund(it, R.color.falseAnswer)
                    falseSound.start()
                }
            }
            changeAnswerStatus()
            btnEnableUnEbale(false_btn, false)
            btnEnableUnEbale(true_btn, false)
            btnEnableUnEbale(cheatButton, false)
        }
        // adding click event to false button- ----------------------------------------------------------------

        false_btn.setOnClickListener {
            checkAnswer(false)
            changeAnswerStatus()
            it.let {
                if (quizViewModel.currentQuestionAnswer == false) {
                    changeBTNBackgraund(it, R.color.trueAnswer)
                    trueSound.start()
                    getGrade(false)
                    showScore()
                } else {
                    changeBTNBackgraund(it, R.color.falseAnswer)
                    falseSound.start()
                }
            }
            btnEnableUnEbale(false_btn, false)
            btnEnableUnEbale(true_btn, false)
            btnEnableUnEbale(cheatButton, false)
        }
        // adding click event to cheat button--------------------------------------------------------------

        cheatButton.setOnClickListener {
            btnEnableUnEbale(cheatButton, false)
            btnEnableUnEbale(false_btn, false)
            btnEnableUnEbale(true_btn, false)
            changeAnswerStatus()
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatingActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        // adding click event to next button--------------------------------------------------------------

        nextBtn.setOnClickListener {
            // currentIndex = (currentIndex + 1) % QuestionBank.size
            checkIndex(nextBtn.id)
            updateQuestion()
        }
        // adding click event to pervius button--------------------------------------------------------------

        previusBtn.setOnClickListener {
            checkIndex(previusBtn.id)
            updateQuestion()
        }
        // adding click event to Question textView--------------------------------------------------------------

        QuestionText.setOnClickListener {
            checkIndex(nextBtn.id)
            updateQuestion()
        }
    }  // end of onCreat function------------------------------------------

    // override for onActivityResult to reciev data from cheatingActivity ---------------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    //  updateQuestion'S defination this function for reset the Question text independ on current index----------------

    private fun updateQuestion() {
        //enable true button false button
        if (quizViewModel.QuestionBank[quizViewModel.currentIndex].answerStatus == false) {
            btnEnableUnEbale(true_btn, true)
            btnEnableUnEbale(false_btn, true)
            btnEnableUnEbale(cheatButton, true)
            quizViewModel.isCheater = false
            changeBTNBackgraund(true_btn, R.color.defaultr)
            changeBTNBackgraund(false_btn, R.color.defaultr)

        } else {
            btnEnableUnEbale(true_btn, false)
            btnEnableUnEbale(false_btn, false)
            btnEnableUnEbale(cheatButton, false)
            msgToast("this question is alrady answered", Gravity.CENTER_VERTICAL)
        }
        QuestionText.setText(quizViewModel.currentQustionText)
        if (quizViewModel.currentIndex == quizViewModel.QuestionBank.size - 1) {
            btnEnableUnEbale(nextBtn, false)
            if (quizViewModel.trueAnswer + quizViewModel.falseAnswer == quizViewModel.QuestionBank.size - 1)
                btnEnableUnEbale(previusBtn, false)

        }

    }

    // checkAnswer is afunction for compare between user answer and stored answer
    //independ on that job it will increamen the trueAnswer counter or falesAnswer-------------------------------------

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when {
            quizViewModel.isCheater -> {
                quizViewModel.falseAnswer++
                quizViewModel.score += 0
                R.string.judgment_toast
            }
            userAnswer == correctAnswer -> {
                quizViewModel.trueAnswer++
                R.string.Correct_toast
            }
            else -> {
                quizViewModel.falseAnswer++
                R.string.wrong_toast
            }

        }

        val toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()


    }

    /* private fun checkCountOfAnswers(): Boolean {
         val checker = (quizViewModel.currentIndex ==
                 quizViewModel.QuestionBank.size - 1
                 && quizViewModel.trueAnswer + quizViewModel.falseAnswer ==
                 quizViewModel.QuestionBank.size)
         return checker

     }*/
    // get grade function will  increase the score if the   answer is correct  -------------------------------------

    private fun getGrade(userAnswer: Boolean) {
        var mark = 0

        if (userAnswer == quizViewModel.QuestionBank[quizViewModel.currentIndex].answer) {
            if (quizViewModel.QuestionBank[quizViewModel.currentIndex].level == "easy") {
                mark = 10
            } else if (quizViewModel.QuestionBank[quizViewModel.currentIndex].level == "medium") {
                mark = 15
            } else if (quizViewModel.QuestionBank[quizViewModel.currentIndex].level == "diff") {
                mark = 25
            }
            quizViewModel.score += mark

            msgToast("your score is : ${quizViewModel.score}%", Gravity.CENTER_VERTICAL)
        }


    }

    private fun msgToast(msg: CharSequence, gravity: Int) {
        // Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.setGravity(gravity, 0, 0)
        toast.show()
    }
    //  checkIndex is function that will call QuizViewModel mov to next or previus independ on te range of
    //QuetionBank list  ------------------------------------------------------------------------------------

    private fun checkIndex(viewId: Int) {
        when (viewId) {
            previusBtn.id -> {
                btnEnableUnEbale(nextBtn, true)
                if (quizViewModel.currentIndex < 1) {
                    quizViewModel.currentIndex = 0
                    btnEnableUnEbale(previusBtn, false)
                } else {
                    btnEnableUnEbale(previusBtn, true)
                    quizViewModel.moveToPreviusQuestion()
                }
            }
            nextBtn.id -> {
                btnEnableUnEbale(previusBtn, true)
                if (quizViewModel.currentIndex >= quizViewModel.QuestionBank.size - 1) {
                    btnEnableUnEbale(nextBtn, false)
                    quizViewModel.currentIndex = quizViewModel.QuestionBank.size - 1
                } else {
                    btnEnableUnEbale(nextBtn, true)
                    quizViewModel.moveToNextQuestion()
                }

            }
        }
    }
      // btnEnableUnEbal is function use to enable view or disable  ---------------------------------------

    private fun btnEnableUnEbale(view: View, BState: Boolean) {
        view.isEnabled = BState

    }
     // changeAnswerStatus() is use to change answer status when question answerd-----------------------------

    private fun changeAnswerStatus() {
        quizViewModel.QuestionBank[quizViewModel.currentIndex].answerStatus = true

    }
     //   randomListOfQustion(list: ArrayList<Question>) this function use to put item to QuestionBank list
     //   from the list that send as parameter randomly  -----------------------------------------------------
     private fun randomListOfQustion(list: ArrayList<Question>) {
        val random = java.util.Random()
        for (i in 1..2) {
            list.let {
                val randIndex = random.nextInt(it.size)
                val randItem: Question = it.get(index = randIndex)
                quizViewModel.QuestionBank.add(randItem)
                it.removeAt(randIndex)
            }
        }
        list.clear()
    }

    // showScore() use to put score in  playerScoreTextview in the activity

    private fun showScore() = this.playerScoreTextview.run {
        text = quizViewModel.score.toString()
    }
   //   changeBTNBackgraund(BtnId: View, color: Int) chang the backround color of view
    private fun changeBTNBackgraund(BtnId: View, color: Int) {

        BtnId.setBackgroundColor(getColor(color))


    }

}