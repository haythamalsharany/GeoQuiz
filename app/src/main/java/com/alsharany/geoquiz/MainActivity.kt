package com.alsharany.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        true_btn.setOnClickListener {
            //Toast.makeText(this,R.string.Correct_toast,Toast.LENGTH_LONG).show()
            var  tost=  Toast.makeText(this,R.string.wrong_toast,Toast.LENGTH_LONG)
            tost.setGravity(Gravity.TOP,0,0)
            tost.show()
        }
        false_btn.setOnClickListener {
           // Toast.makeText(this,R.string.wrong_toast,Toast.LENGTH_LONG).show()
       var  tost=  Toast.makeText(this,R.string.wrong_toast,Toast.LENGTH_LONG)
            tost.setGravity(Gravity.TOP,0,0)
            tost.show()


        }
    }
}