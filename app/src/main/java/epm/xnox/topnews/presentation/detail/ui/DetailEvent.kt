package epm.xnox.topnews.presentation.detail.ui

import epm.xnox.topnews.domain.model.Article

sealed class DetailEvent {
    data class InsertArticle(val article: Article) : DetailEvent()
    data class DeleteArticle(val id: Int) : DetailEvent()
}