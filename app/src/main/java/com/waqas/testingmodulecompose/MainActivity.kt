package com.waqas.testingmodulecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
//import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.waqas.core.domain.preferences.Preferences
import com.waqas.testingmodulecompose.navigation.Route
import com.waqas.onboarding_presentation.WelcomeScreen
import com.waqas.onboarding_presentation.activity.ActivityLevelScreen
import com.waqas.onboarding_presentation.age.AgeScreen
import com.waqas.onboarding_presentation.gender.GenderScreen
import com.waqas.onboarding_presentation.goal.GoalScreen
import com.waqas.onboarding_presentation.height.HeightScreen
import com.waqas.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.waqas.onboarding_presentation.weight.WeightScreen
import com.waqas.testingmodulecompose.ui.theme.TempComposeTheme
import com.waqas.tracker_presentation.search.SearchScreen
import com.waqas.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var preferences: Preferences


    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
//        enableEdgeToEdge()
        setContent {
            TempComposeTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (shouldShowOnboarding) Route.WELCOME else Route.TRACKER_OVERVIEW
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNextClick = { navController.navigate(Route.GENDER) })
                        }
                        composable(Route.ACTIVITY) {
                            innerPadding.calculateTopPadding()
                            ActivityLevelScreen(onNextClick = { navController.navigate(Route.GOAL) })
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = { navController.navigate(Route.HEIGHT) }
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNextClick = { navController.navigate(Route.AGE) })
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = { navController.navigate(Route.WEIGHT) }
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                onNextClick = { navController.navigate(Route.ACTIVITY) },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNextClick = { navController.navigate(Route.NUTRIENT_GOAL) })
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                onNextClick = { navController.navigate(Route.TRACKER_OVERVIEW) },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = androidx.navigation.NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = androidx.navigation.NavType.IntType
                                },
                                navArgument("month") {
                                    type = androidx.navigation.NavType.IntType
                                },
                                navArgument("year") {
                                    type = androidx.navigation.NavType.IntType
                                }

                            )
                        ) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                onNavigateUp = { navController.navigateUp() },
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year

                            )
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { mealName, day, month, year ->
                                navController.navigate(
                                    Route.SEARCH + "/$mealName" + "/$day" + "/$month" + "/$year"
                                )


                            })
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TempComposeTheme {
        Greeting("Android")
    }
}