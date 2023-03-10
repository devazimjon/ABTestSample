package uz.devazimjon.sample.abtest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.devazimjon.sample.abtest.MainState.MainBackgroundColor.BLACK
import uz.devazimjon.sample.abtest.MainState.MainBackgroundColor.WHITE
import java.util.UUID

@HiltViewModel
class MainViewModel : ViewModel() {

    val state: MutableState<MainState> = mutableStateOf(
        MainState(
            enableLegacy = true,
            mainBackgroundColor = WHITE,
            descriptionText = MainState.buildDescriptionText(WHITE),
            toggleBackgroundColor = ::toggleBackgroundColor,
        )
    )

    fun syncRemoteConfig() {
        val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            onRemoteConfigSyncComplete(task)
        }
        if (remoteConfig.getValue("compose_ui").asBoolean()) {
            disableLegacyUI()
        } else {
            enableLegacyUI()
        }
    }

    private fun onRemoteConfigSyncComplete(task: Task<Boolean>){
        state.value = state.value.copy(removeConfigSyncToken = UUID.randomUUID().toString())
        if (task.isSuccessful) {
            val updated: Boolean = task.result
            println("Fetch and activate succeeded: ${updated}")
        } else {
            println("Fetch failed.")
        }
        println("compose_ui: ${FirebaseRemoteConfig.getInstance().getValue("compose_ui").asBoolean()}")
    }

    private fun toggleBackgroundColor() {
        val newBackgroundColor = if (state.value.mainBackgroundColor == WHITE) {
            BLACK
        } else {
            WHITE
        }
        state.value = state.value.copy(
            mainBackgroundColor = newBackgroundColor,
            descriptionText = MainState.buildDescriptionText(newBackgroundColor)
        )
    }


    private fun disableLegacyUI() {
        state.value = state.value.copy(
            enableLegacy = false
        )
    }

    private fun enableLegacyUI() {
        state.value = state.value.copy(
            enableLegacy = true
        )
    }
}