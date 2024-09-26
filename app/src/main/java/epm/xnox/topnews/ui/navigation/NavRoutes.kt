package epm.xnox.topnews.ui.navigation

sealed class NavRoutes(val route: String) {

    data object ScreenHome : NavRoutes("home")

    data object ScreenDetail : NavRoutes("detail")

    data object ScreenMark : NavRoutes("mark")

    data object ScreenSearch : NavRoutes("search")
}