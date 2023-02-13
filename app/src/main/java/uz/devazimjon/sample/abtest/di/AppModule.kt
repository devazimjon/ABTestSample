package uz.devazimjon.sample.abtest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.devazimjon.sample.abtest.MainState
import uz.devazimjon.sample.abtest.domain.ComposeLayoutRunner
import uz.devazimjon.sample.abtest.domain.LegacyLayoutRunner
import uz.devazimjon.sample.abtest.layout.runners.MainComposeLayoutRunner
import uz.devazimjon.sample.abtest.layout.runners.MainLegacyLayoutRunner

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindComposeLayout(
        runner: MainComposeLayoutRunner
    ): ComposeLayoutRunner<MainState>

    @Binds
    fun bindLegacyLayout(
        runner: MainLegacyLayoutRunner
    ): LegacyLayoutRunner<MainState>
}