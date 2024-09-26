package epm.xnox.topnews.presentation.detail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.topnews.domain.model.Article
import epm.xnox.topnews.domain.useCase.database.DeleteArticleUseCase
import epm.xnox.topnews.domain.useCase.database.InsertArticleUseCase
import epm.xnox.topnews.presentation.detail.ui.DetailEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val insertArticleUseCase: InsertArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) :ViewModel() {

    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.DeleteArticle -> deleteArticle(event.id)
            is DetailEvent.InsertArticle -> insertArticle(event.article)
        }
    }

    private fun insertArticle(article: Article) {
        viewModelScope.launch {
            insertArticleUseCase.invoke(article)
        }
    }

    private fun deleteArticle(id: Int) {
        viewModelScope.launch {
            deleteArticleUseCase.invoke(id)
        }
    }
}