package com.example.capstonedesign.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstonedesign.repository.BoardRepository

class BoardViewModelFactory(private val boardRepository: BoardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BoardViewModel::class.java)) {
            return BoardViewModel(boardRepository) as T
        }
        throw IllegalArgumentException("ViewModel class is not found")
    }
}