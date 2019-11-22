package com.androidacademy.angel.advertisementListPackage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class MenuItemName {
    SEARCH,
    FILTER,
    SETTINGS
}
class OptionMenuViewModel: ViewModel() {
    private val eventHandler: MutableLiveData<MenuItemName> = MutableLiveData()
    val menuClicked: LiveData<MenuItemName> = eventHandler

    internal fun optionClicked(name: MenuItemName){
        eventHandler.value = name
    }
}