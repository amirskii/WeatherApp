package com.example.weatherapp.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest : BaseTest() {

    @get:Rule
    val coroutineRule = MainCoroutineRule()
}