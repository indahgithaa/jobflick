package com.example.jobflick.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jobflick.features.onboarding.presentation.OnboardingScreen
import com.example.jobflick.features.onboarding.presentation.SplashScreen
import com.example.jobflick.features.onboarding.presentation.RoleSelectionScreen
import com.example.jobflick.features.auth.presentation.*

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Routes.SPLASH
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Routes.SELECTROLE) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.SELECTROLE) {
            RoleSelectionScreen(
                onJobSeeker = { navController.navigate(Routes.signup("jobseeker")) },
                onRecruiter = { navController.navigate(Routes.signup("recruiter")) }
            )
        }

        composable(
            route = Routes.SIGNUP,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "jobseeker"

            SignUpRoute(
                role = role,
                onSignedUp = {
                    navController.navigate(Routes.done(role)) {
                        popUpTo(Routes.signup(role)) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onClickSignIn = {
                    navController.navigate(Routes.signin(role)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Routes.SIGNIN,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "jobseeker"

            SignInRoute(
                onSignedIn = {
                    // Navigate to Done screen after successful sign-in
                    navController.navigate(Routes.done(role)) {
                        popUpTo(Routes.signin(role)) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onClickSignUp = {
                    navController.navigate(Routes.signup(role)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Routes.DONE,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "jobseeker"

            JobSeekerDoneRegistScreen(
                onStart = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.DONE) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.HOME) {
            // TODO: add HomeScreen()
        }
    }
}
