package com.alsharany.geoquiz

import androidx.annotation.StringRes

data class Question( @StringRes val Question_text:Int,val answer:Boolean,var answerStatus:Boolean,val level:String,var mark:Int) {
}