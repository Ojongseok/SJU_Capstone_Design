package com.example.capstonedesign.model.board

import java.io.File

data class WritePostRequest(
    val title: String,
    val content: String,
    val tag: String
)
