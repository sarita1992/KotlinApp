package com.example.viesuretest.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.viesuretest.model.BookData
import com.example.viesuretest.retrofitManager.UniversityRepo
import com.example.viesuretest.utils.UseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException


class UniConfigViewModel(private val universityRepo: UniversityRepo) : ViewModel(),
    CoroutineScope {

    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

     var bookListUIState: MutableStateFlow<List<BookData>> = MutableStateFlow(
        listOf()
    )
    private val _errorMessage = MutableStateFlow("")
    val showError = _errorMessage.asStateFlow()




    fun getBookData() {
        launch {
            try {
                doLongRunningTask()
                    .flowOn(Dispatchers.Default)
                    .retry(retries = 3) { cause ->
                        if (true) {
                            delay(2000)
                            return@retry true
                        } else {
                            return@retry false
                        }
                    }
                    .catch {
                        _errorMessage.value= it.message.toString()
                    }
                    .collect {
                    }
            } catch (e: Exception) {
                Log.e(this.toString(), e.message ?: "")
            }
        }
    }

    private fun doLongRunningTask(): Flow<Int> {
        return flow {
            delay(2000)
                val result=    withContext(Dispatchers.IO) {
                   universityRepo.getBookData()
                }
               when (result) {
                   is UseCaseResult.Success -> bookListUIState.value = result.data
                   is UseCaseResult.Error ->{

                       /**
                        * This case handle all type of exception error but we can apply specific error handling as well
                        * Like 404 we written here, similar we can do for 500, 401 etc.
                       * */
//                       if(result.exception.message.equals("HTTP 404 Not Found"))
                       if(true)
                           throw result.exception
                       delay(2000)
                       emit(0)
                   }
               }
        }
    }
}