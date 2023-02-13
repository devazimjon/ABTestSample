package uz.devazimjon.sample.abtest.layout.runners

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.devazimjon.sample.abtest.MainState
import uz.devazimjon.sample.abtest.MainState.MainBackgroundColor.WHITE
import uz.devazimjon.sample.abtest.R
import uz.devazimjon.sample.abtest.domain.ComposeLayoutRunner
import javax.inject.Inject

class MainComposeLayoutRunner @Inject constructor() : ComposeLayoutRunner<MainState> {

    @Composable
    override fun Create(state: MainState) {
        val backgroundColor = if (state.mainBackgroundColor == WHITE) {
            Color.White
        } else {
            Color.Black
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ){
            Column(
                modifier = Modifier.padding(16.dp)
            ){
                val textColor = if (state.mainBackgroundColor == WHITE) {
                    Color.Black
                } else {
                    Color.White
                }
                Text(
                    text = state.descriptionText,
                    color = textColor,
                    fontSize = 18.sp
                )
                Row(){
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(R.string.toggle_background_color),
                        color = textColor,
                        fontSize = 14.sp,
                    )
                    Switch(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        checked = state.mainBackgroundColor == WHITE,
                        onCheckedChange = {
                            state.toggleBackgroundColor()
                        },
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(id = R.string.compose_text),
                    color = colorResource(id = R.color.red),
                    fontSize = 24.sp
                )
            }
        }
    }
}