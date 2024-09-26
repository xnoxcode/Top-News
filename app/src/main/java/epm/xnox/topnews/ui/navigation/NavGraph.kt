package epm.xnox.topnews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import epm.xnox.topnews.presentation.SharedViewModel
import epm.xnox.topnews.presentation.detail.ui.ScreenDetail
import epm.xnox.topnews.presentation.detail.viewModel.DetailViewModel
import epm.xnox.topnews.presentation.home.ui.ScreenHome
import epm.xnox.topnews.presentation.home.viewModel.HomeViewModel
import epm.xnox.topnews.presentation.mark.ui.ScreenMark
import epm.xnox.topnews.presentation.mark.viewModel.MarkViewModel
import epm.xnox.topnews.presentation.search.ui.ScreenSearch
import epm.xnox.topnews.presentation.search.viewModel.SearchViewModel

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()
    val sharedViewModel = hiltViewModel<SharedViewModel>()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NavRoutes.ScreenHome.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            ScreenHome(
                homeViewModel,
                sharedViewModel,
                onNavigateToDetail = {
                    navController.navigate(NavRoutes.ScreenDetail.route)
                },
                onNavigateToMark = {
                    navController.navigate(NavRoutes.ScreenMark.route)
                },
                onNavigateToSearch = {
                    navController.navigate(NavRoutes.ScreenSearch.route)
                }
            )
        }

        composable(route = NavRoutes.ScreenDetail.route) {
            val detailViewModel = hiltViewModel<DetailViewModel>()
            ScreenDetail(detailViewModel, sharedViewModel) {
                navController.popBackStack()
            }
        }

        composable(route = NavRoutes.ScreenMark.route) {
            val markViewModel = hiltViewModel<MarkViewModel>()
            ScreenMark(
                markViewModel,
                sharedViewModel,
                onNavigateToDetail = {
                    navController.navigate(NavRoutes.ScreenDetail.route)
                },
                onNavigateToBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = NavRoutes.ScreenSearch.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            ScreenSearch(
                searchViewModel,
                sharedViewModel,
                onNavigateToDetail = { navController.navigate(NavRoutes.ScreenDetail.route) },
                onNavigateToBack = { navController.popBackStack() })
        }
    }
}