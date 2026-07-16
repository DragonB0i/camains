package com.example.trl.ai

data class AIResult(

    val answer: String,

    val confidence: Int,

    val modelsUsed: Int,

    val judgeUsed: Boolean

)