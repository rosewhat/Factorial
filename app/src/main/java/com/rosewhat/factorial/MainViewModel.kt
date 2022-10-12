package com.rosewhat.factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val scope = CoroutineScope(CoroutineName("My coroutine scope"))

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state


    fun calculate(value: String) {
        _state.value = Progress
        if (value.isBlank()) {
            _state.value = Error
            return
        }
        scope.launch(Dispatchers.Main) {
            val number = value.toLong()
                // возвращает значение
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }

            _state.value = Result(result)
        }
    }

    fun factorial(number: Long): String {

        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}