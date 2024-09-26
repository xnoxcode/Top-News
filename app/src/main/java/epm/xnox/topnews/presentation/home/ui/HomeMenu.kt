package epm.xnox.topnews.presentation.home.ui

sealed class HomeMenu(val name: String) {
    data object Mark : HomeMenu("Marcadas")
}