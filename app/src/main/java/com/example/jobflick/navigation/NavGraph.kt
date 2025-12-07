package com.example.jobflick.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jobflick.features.auth.presentation.OnboardingScreen
import com.example.jobflick.features.auth.presentation.SplashScreen
import com.example.jobflick.features.auth.presentation.RoleSelectionScreen
import com.example.jobflick.features.auth.presentation.*   // SignUpRoute, SignInRoute, CompleteProfileScreen, JobSeekerDoneRegistScreen
import com.example.jobflick.features.auth.discover.presentation.DiscoverScreen

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

        // SIGN UP
        composable(
            route = Routes.SIGNUP,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "jobseeker"

            SignUpRoute(
                role = role,
                onSignedUp = {
                    navController.navigate(Routes.completeProfile(role)) {
                        popUpTo(Routes.signup(role)) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onClickSignIn = {
                    navController.navigate(Routes.signin(role)) { launchSingleTop = true }
                }
            )
        }

        // COMPLETE PROFILE (setelah signup)
        composable(
            route = Routes.COMPLETE_PROFILE,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "jobseeker"

            CompleteProfileScreen(
                role = role,
                onPickPhoto = { /* open photo picker */ },
                onPickCV = { /* open file picker */ },
                onSubmit = { _, _, _, _, _, _, _, _, _ ->
                    navController.navigate(Routes.done(role)) {
                        // ✅ pakai completeProfile(role) (camelCase), bukan complete_profile
                        popUpTo(Routes.completeProfile(role)) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // SIGN IN (klik "masuk" dari signup)
        composable(
            route = Routes.SIGNIN,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "jobseeker"

            SignInRoute(
                onSignedIn = {
                    navController.navigate(Routes.DISCOVER) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onClickSignUp = {
                    navController.navigate(Routes.signup(role)) { launchSingleTop = true }
                }
            )
        }

        // DONE (setelah complete profile)
        composable(
            route = Routes.DONE,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) {
            JobSeekerDoneRegistScreen(
                onStart = {
                    navController.navigate(Routes.DISCOVER) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // DISCOVER
        composable(Routes.DISCOVER) {
            DiscoverScreen(
                onApply = { /* TODO: navigate to ItsAMatch */ },
                onSave = { /* TODO */ }
            )
        }
    }
}
