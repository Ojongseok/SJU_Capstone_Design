package sju.sejong.capstonedesign.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sju.sejong.capstonedesign.repository.BoardRepository
import sju.sejong.capstonedesign.viewmodel.BoardViewModel

class BoardViewModelFactory(private val boardRepository: BoardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BoardViewModel::class.java)) {
            return BoardViewModel(boardRepository) as T
        }
        throw IllegalArgumentException("ViewModel class is not found")
    }
}