package com.rosewhat.factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName(NAME_COROUTINE))

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        scope.launch {
            delay(1000)
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _state.value = Result(factorial = result.toString())
        }


    }

    private fun factorial(number: Long): String {
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

    companion object {
        private const val NAME_COROUTINE = "MyCoroutineScope"
    }

}


