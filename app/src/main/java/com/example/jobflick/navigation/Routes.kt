package com.example.jobflick.navigation

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val SELECTROLE = "select_role"

    const val SIGNUP = "signup/{role}"
    const val SIGNIN = "signin/{role}"

    const val DONE = "done/{role}"
    const val HOME = "home"

    fun signup(role: String) = "signup/${role.lowercase()}"
    fun signin(role: String) = "signin/${role.lowercase()}"
    fun done(role: String) = "done/${role.lowercase()}"
}
