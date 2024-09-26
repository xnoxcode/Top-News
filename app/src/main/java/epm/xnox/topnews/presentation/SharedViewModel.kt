package epm.xnox.topnews.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.topnews.domain.model.Article
import epm.xnox.topnews.domain.useCase.preference.ReadDateUseCase
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val readDateUseCase: ReadDateUseCase): ViewModel() {

    var article by mutableStateOf<Article?>(null)
        private set

    fun addArticle(newArticle: Article) {
        article = newArticle
    }
}