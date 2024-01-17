package com.example.quizeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizeapp.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: QuizListAdapter
    lateinit var QuizeModelList : MutableList<quizemodell>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        QuizeModelList = mutableListOf()
        //getdatafromOff()
        getDataFromFirebase()

    }

    private fun getDataFromFirebase() {
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {dataSnapshot->
                if (dataSnapshot.exists()){
                  for (snapshot in dataSnapshot.children){
                      val Quizemodell =snapshot.getValue(quizemodell::class.java)
                      if (Quizemodell != null) {
                          QuizeModelList.add(Quizemodell)
                      }
                  }
                }
                setupRecyclerView()
            }
    }

    private fun getdatafromOff()
    {


        val listModel = mutableListOf<QuestionModel>()
        listModel.add(QuestionModel("What Is Java",mutableListOf("Diamond","animal","Lang","None"),"Lang"))
        listModel.add(QuestionModel("What Is Rubi",mutableListOf("Diamond","animal","Lang","None"),"Lang"))
        listModel.add(QuestionModel("What Is python",mutableListOf("Diamond","animal","Lang","None"),"Lang"))

        QuizeModelList.add(quizemodell("1","Programing","All The Basic Quiz Of Programing","10",listModel))
//        QuizeModelList.add(quizemodell("2","Computer","All The Basic Quiz Of Computer","15"))
//        QuizeModelList.add(quizemodell("3","Science","All The Basic Quiz Of Science","20"))
//        QuizeModelList.add(quizemodell("4","Math","All The Basic Quiz Of Math","10"))
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = QuizListAdapter(QuizeModelList)
        binding.RecyclerView.layoutManager =LinearLayoutManager(this)
        binding.RecyclerView.adapter =adapter

    }


}