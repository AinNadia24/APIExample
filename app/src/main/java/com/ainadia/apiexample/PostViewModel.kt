package com.ainadia.apiexample
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostViewModel : ViewModel(){
    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val posts: LiveData<List<Post>> = _posts

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error:State<String?> = _error

    fun fetchPosts(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _posts.value = RetrofitInstance.apiService.getPosts()
                _error.value = null
            }catch (e:Exception){
                _error.value = "Failed to load data ${e.message}"
                Log.e("PostViewModel", "Error: $e")
            }
            finally {
                _isLoading.value = false
            }
        }
    }

}
