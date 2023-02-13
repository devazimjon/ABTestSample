package uz.devazimjon.sample.abtest.domain

import androidx.compose.runtime.Composable

interface ComposeLayoutRunner<T> {

    @Composable
    fun Create(state: T)
}
