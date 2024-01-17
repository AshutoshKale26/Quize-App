package com.example.quizeapp


data class quizemodell(
    val id : String,
    val tittle :String,
    val subtittle:String,
    val time :String,
    val questionList: List<QuestionModel>
)
{
    constructor() : this ("","","","", emptyList())
}

data class QuestionModel (
    val question : String,
    val option: List<String>,
    val correct :String
)
{
    constructor() : this("", emptyList(),"")
}