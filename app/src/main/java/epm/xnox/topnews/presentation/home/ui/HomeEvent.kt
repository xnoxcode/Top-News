package epm.xnox.topnews.presentation.home.ui

sealed class HomeEvent {
    data class SetWorld(val world: String) : HomeEvent()
    data class SaveDate(val date: String) : HomeEvent()
    data object ReadDate : HomeEvent()
    data object GetTopNews : HomeEvent()
    data object GetAllNews : HomeEvent()
}