package epm.xnox.topnews.presentation.mark.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.topnews.domain.model.Article
import epm.xnox.topnews.domain.useCase.database.DeleteAllArticlesUseCase
import epm.xnox.topnews.domain.useCase.database.GetAllArticlesUseCase
import epm.xnox.topnews.presentation.mark.ui.MarkEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkViewModel @Inject constructor(
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val deleteAllArticlesUseCase: DeleteAllArticlesUseCase
): ViewModel() {

    private val _articles = mutableStateOf<List<Article>>(emptyList())
    val articles : State<List<Article>> = _articles

    init {
        onEvent(MarkEvent.GetAllArticles)
    }

    fun onEvent(event: MarkEvent) {
        when(event) {
            MarkEvent.DeleteAllArticles -> deleteAllArticles()
            MarkEvent.GetAllArticles -> getAllArticles()
        }
    }

    private fun getAllArticles() {
        viewModelScope.launch {
            getAllArticlesUseCase.invoke().collect { result ->
                _articles.value = result
            }
        }
    }

    private fun deleteAllArticles() {
       viewModelScope.launch {
           deleteAllArticlesUseCase.invoke()
       }
    }
}