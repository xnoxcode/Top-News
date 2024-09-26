package epm.xnox.topnews.presentation.search.ui

sealed class SearchEvent {
    data object SearchArticle : SearchEvent()
    data object ReadDate : SearchEvent()
    data class SetWorld(val world: String) : SearchEvent()
}