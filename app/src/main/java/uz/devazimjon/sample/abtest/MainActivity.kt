package uz.devazimjon.sample.abtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import dagger.hilt.android.AndroidEntryPoint
import uz.devazimjon.sample.abtest.domain.ComposeLayoutRunner
import uz.devazimjon.sample.abtest.domain.LegacyLayoutRunner
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainComposeLayoutRunner: ComposeLayoutRunner<MainState>

    @Inject
    lateinit var mainLegacyLayoutRunner: LegacyLayoutRunner<MainState>

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.state.value.removeConfigSyncToken == null) {
            viewModel.syncRemoteConfig()
        }
        setContent {
            val state = viewModel.state.value
            if (state.enableLegacy) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        mainLegacyLayoutRunner.create(context, R.layout.activity_main)
                    },
                    update = { view ->
                        mainLegacyLayoutRunner.update(view = view, state = state)
                    }
                )
            } else {
                mainComposeLayoutRunner.Create(state)
            }
        }
    }
}
