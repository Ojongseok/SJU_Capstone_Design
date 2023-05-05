package sju.sejong.capstonedesign.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sju.sejong.capstonedesign.repository.MainRepository
import sju.sejong.capstonedesign.viewmodel.MainViewModel

class MainViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("ViewModel class is not found")
    }
}