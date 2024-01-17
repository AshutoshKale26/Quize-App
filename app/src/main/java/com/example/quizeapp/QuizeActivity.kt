package com.example.quizeapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.quizeapp.databinding.ActivityQuizeBinding
import com.example.quizeapp.databinding.ActivityScoreDialogeBinding

class QuizeActivity : AppCompatActivity(),View.OnClickListener {
    companion object{
        var questionModelList : List<QuestionModel> = listOf()
        var time : String=""
    }

    lateinit var binding: ActivityQuizeBinding
    var currencyQuestionIndex = 0;
    var selectedAnswer =""
    var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btn0.setOnClickListener(this@QuizeActivity)
            btn1.setOnClickListener(this@QuizeActivity)
            btn2.setOnClickListener(this@QuizeActivity)
            btn3.setOnClickListener(this@QuizeActivity)
            Next.setOnClickListener(this@QuizeActivity)

        }

        loadQuestion()
        startTimer()

    }

    private fun startTimer() {
        val totalTime = time.toInt()* 60 * 1000L
        object : CountDownTimer(totalTime,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished /1000
                val minites = seconds/60
                val remainingSeconds = seconds % 60
                binding.quitionTimingTextView.text =String.format("%02d:%02d",minites,remainingSeconds)
            }

            override fun onFinish() {
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestion (){

        selectedAnswer =""

        if(currencyQuestionIndex == questionModelList.size){
            finishQuize()
            return
        }

        binding.apply {
            binding.questionIndicatorTextView.text="Question ${currencyQuestionIndex+1} /${questionModelList.size}"
            ProgressIndicator.progress=
                ( currencyQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            binding.questionTextView.text = questionModelList[currencyQuestionIndex].question
            binding.btn0.text = questionModelList[currencyQuestionIndex].option[0]
            binding.btn1.text = questionModelList[currencyQuestionIndex].option[1]
            binding.btn2.text = questionModelList[currencyQuestionIndex].option[2]
            binding.btn3.text = questionModelList[currencyQuestionIndex].option[3]

        }
    }

    override fun onClick(view: View?) {



        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.GREY))
            btn1.setBackgroundColor(getColor(R.color.GREY))
            btn2.setBackgroundColor(getColor(R.color.GREY))
            btn3.setBackgroundColor(getColor(R.color.GREY))
        }

        val clickedBtn = view as Button
        if(clickedBtn.id==R.id.Next){

            if (selectedAnswer.isEmpty()){
                Toast.makeText(applicationContext,"Please Select The Answer Then Click Next Button",Toast.LENGTH_SHORT).show()
                return;
            }

            if (selectedAnswer == questionModelList[currencyQuestionIndex].correct)
            {
                score += 1
                Log.i("score of Quize ",score.toString())
            }

            currencyQuestionIndex++
            loadQuestion()
        }else{
            selectedAnswer =clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }

    private fun finishQuize() {
        val totalQuestion = questionModelList.size
        val percentage = (score.toFloat()/totalQuestion.toFloat() * 100).toInt()

        val DialogBinding = ActivityScoreDialogeBinding.inflate(layoutInflater)
        DialogBinding.apply {
            CircularProgressIndicator.progress =percentage
            scoreIndicator.text = "$percentage%"
            if (percentage>60)
            {
                scoreTittle.text =" Congrates You Have Passed "
            }else{
                scoreTittle.text =" Opps!!! You Have Failed"
            }
            scoreSubTittle.text = "$score out Of $totalQuestion are Correct "
            finishBtn.setOnClickListener{
                finish()}
        }
        AlertDialog.Builder(this)
            .setView(DialogBinding.root)
            .setCancelable(false)
            .show()
    }
}