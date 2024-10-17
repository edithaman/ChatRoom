package com.example.chatroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.data.Result
import com.example.chatroom.data.Room
import com.example.chatroom.data.RoomRepository
import com.example.chatroom.ui.theme.Injection
import kotlinx.coroutines.launch


class RoomViewModel : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val _roomRepository: RoomRepository
    init {
        _roomRepository = RoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name: String) {
        viewModelScope.launch {
            _roomRepository.createRoom(name)
        }
    }

    fun loadRooms() {
        viewModelScope.launch {
            when (val result = _roomRepository.getRooms()) {
                is Result.Success -> _rooms.value = result.data
                else -> {

                }
            }
        }
    }

}