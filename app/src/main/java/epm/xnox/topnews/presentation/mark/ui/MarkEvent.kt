package epm.xnox.topnews.presentation.mark.ui

sealed class MarkEvent {
    data object GetAllArticles : MarkEvent()
    data object DeleteAllArticles : MarkEvent()
}