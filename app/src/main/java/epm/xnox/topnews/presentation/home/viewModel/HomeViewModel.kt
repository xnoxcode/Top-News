package epm.xnox.topnews.presentation.home.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.topnews.core.common.Result
import epm.xnox.topnews.domain.useCase.network.GetAllNewsUseCase
import epm.xnox.topnews.domain.useCase.network.GetTopNewsUseCase
import epm.xnox.topnews.domain.useCase.preference.ReadDateUseCase
import epm.xnox.topnews.domain.useCase.preference.SaveDateUseCase
import epm.xnox.topnews.presentation.home.ui.HomeCategory
import epm.xnox.topnews.presentation.home.ui.HomeEvent
import epm.xnox.topnews.presentation.home.ui.NewsState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val getTopNewsUseCase: GetTopNewsUseCase,
    private val readDateUseCase: ReadDateUseCase,
    private val saveDateUseCase: SaveDateUseCase
) : ViewModel() {

    private val _topNewsState = mutableStateOf(NewsState())
    val topNewsState: State<NewsState> = _topNewsState

    private val _allNewsState = mutableStateOf(NewsState())
    val allNewsState: State<NewsState> = _allNewsState

    private val _searchWorld = mutableStateOf(HomeCategory.Tecnología.name)
    val searchWorld: State<String> = _searchWorld

    private val _searchDate = mutableStateOf("")
    val searchDate: State<String> = _searchDate

    init {
        onEvent(HomeEvent.ReadDate)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetTopNews -> getTopNews()

            HomeEvent.GetAllNews -> getAllNews()

            HomeEvent.ReadDate -> readDate()

            is HomeEvent.SaveDate -> saveDate(event.date)

            is HomeEvent.SetWorld ->_searchWorld.value = event.world
        }
    }

    private fun saveDate(date: String) {
        viewModelScope.launch {
            saveDateUseCase.invoke(date)
        }
    }

    private fun readDate() {
        viewModelScope.launch {
            readDateUseCase.invoke().collect { date ->
                _searchDate.value = date
                onEvent(HomeEvent.GetTopNews)
                onEvent(HomeEvent.GetAllNews)
            }
        }
    }

    private fun getTopNews() {
        viewModelScope.launch {
            getTopNewsUseCase.invoke().collect { result ->
                when (result) {
                    Result.Loading -> {
                        _topNewsState.value = NewsState(loading = true)
                    }

                    is Result.Error -> {
                        _topNewsState.value = NewsState(error = result.message)
                    }

                    is Result.Success -> {
                        _topNewsState.value = NewsState(data = result.data)
                    }
                }
            }
        }
    }

    private fun getAllNews() {
        viewModelScope.launch {
            getAllNewsUseCase.invoke(_searchWorld.value, _searchDate.value).collect { result ->
                when (result) {
                    Result.Loading -> {
                        _allNewsState.value = NewsState(loading = true)
                    }

                    is Result.Error -> {
                        _allNewsState.value = NewsState(error = result.message)
                    }

                    is Result.Success -> {
                        _allNewsState.value = NewsState(data = result.data)
                    }
                }
            }
        }
    }
}