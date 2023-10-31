package com.example.neoquizapp.viewModel.MainViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FilterViewModel : ViewModel() {
    private val _selectedFilter = MutableLiveData<Int?>()
    val selectedFilter: MutableLiveData<Int?>
        get() = _selectedFilter

    fun setFilter(filter: Int?) {
        _selectedFilter.value = filter
    }
}
