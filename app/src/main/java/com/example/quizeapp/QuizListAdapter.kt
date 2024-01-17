package com.example.quizeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.quizeapp.databinding.ActivityMainBinding
import com.example.quizeapp.databinding.QuizeItemRecyclerViewBinding

class QuizListAdapter(private val quizeModelList: List<quizemodell>):
    RecyclerView.Adapter<QuizListAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: QuizeItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
    {
        @SuppressLint("SetTextI18n")
        fun bind (model: quizemodell){
            binding.apply {
                quizTittleText.text=model.tittle
                quizSubtittleText.text=model.subtittle
                binding.quizeTimeText.text=model.time+"min"
                root.setOnClickListener{
                    val intent = Intent(root.context,QuizeActivity::class.java)
                    QuizeActivity.questionModelList = model.questionList
                    QuizeActivity.time = model.time
                    root.context.startActivity(intent)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =QuizeItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizeModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizeModelList[position])
    }
}