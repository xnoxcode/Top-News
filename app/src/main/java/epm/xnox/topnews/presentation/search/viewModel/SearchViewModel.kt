package epm.xnox.topnews.presentation.search.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.topnews.core.common.Result
import epm.xnox.topnews.domain.useCase.network.GetAllNewsUseCase
import epm.xnox.topnews.domain.useCase.preference.ReadDateUseCase
import epm.xnox.topnews.presentation.search.ui.SearchEvent
import epm.xnox.topnews.presentation.search.ui.SearchState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val readDateUseCase: ReadDateUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _searchWorld = mutableStateOf("")
    val searchWorld: State<String> = _searchWorld

    private val _searchDate = mutableStateOf("")
    val searchDate: State<String> = _searchDate

    init {
        onEvent(SearchEvent.ReadDate)
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.ReadDate -> readDate()

            is SearchEvent.SearchArticle -> searchArticle()

            is SearchEvent.SetWorld -> _searchWorld.value = event.world
        }
    }

    private fun readDate() {
        viewModelScope.launch {
            readDateUseCase.invoke().collect { date ->
                _searchDate.value = date
            }
        }
    }

    private fun searchArticle() {
        viewModelScope.launch {
            getAllNewsUseCase.invoke(_searchWorld.value, _searchDate.value)
                .collect { result ->
                    when (result) {
                        Result.Loading -> {
                            _state.value = SearchState(loading = true)
                        }

                        is Result.Error -> {
                            _state.value = SearchState(error = result.message)
                        }

                        is Result.Success -> {
                            _state.value = SearchState(data = result.data)
                        }
                    }
                }
        }
    }
}