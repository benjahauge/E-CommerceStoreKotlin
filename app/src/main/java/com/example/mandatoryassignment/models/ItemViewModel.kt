package com.example.mandatoryassignment.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mandatoryassignment.models.Item
import com.example.mandatoryassignment.repository.ResaleRepository

class ItemViewModel : ViewModel() {
    private val repository = ResaleRepository()
    val itemsLiveDataMutable: MutableLiveData<List<Item>> = repository.itemsLiveData
    val itemsLiveData: LiveData<List<Item>> = itemsLiveDataMutable
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val price: MutableLiveData<Item> = MutableLiveData()

    init {
        reload()
    }

    fun sortByPrice() {
        itemsLiveDataMutable.value = itemsLiveDataMutable.value?.sortedBy { it.price }
    }

    fun sortByPriceDescinding() {
        itemsLiveDataMutable.value = itemsLiveDataMutable.value?.sortedByDescending { it.price }

    }

    fun filterByPrice(maxVal: Int, minVal: Int) {
        itemsLiveDataMutable.value = itemsLiveDataMutable.value?.filter { it.price in (minVal + 1) until maxVal }
    }



    fun reload() {
        repository.getPosts()
    }

    operator fun get(index: Int): Item? {
        return itemsLiveData.value?.get(index)
    }



    fun add(item: Item) {
        repository.add(item)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}