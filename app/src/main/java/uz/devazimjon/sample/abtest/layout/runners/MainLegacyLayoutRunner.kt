package uz.devazimjon.sample.abtest.layout.runners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import uz.devazimjon.sample.abtest.MainState
import uz.devazimjon.sample.abtest.MainState.MainBackgroundColor.WHITE
import uz.devazimjon.sample.abtest.R
import uz.devazimjon.sample.abtest.domain.LegacyLayoutRunner
import javax.inject.Inject

class MainLegacyLayoutRunner @Inject constructor() : LegacyLayoutRunner<MainState> {

    override fun create(context: Context, layoutId: Int): View {
        return LayoutInflater.from(context).inflate(layoutId, null, false)
    }

    override fun update(view: View, state: MainState) {
        val switch: Switch = view.findViewById(R.id.background_switch)
        val textView: TextView = view.findViewById(R.id.text_view)
        val constraintMain: ConstraintLayout = view.findViewById(R.id.constraint_main)
        switch.isChecked = state.mainBackgroundColor == WHITE
        val textColor = if (state.mainBackgroundColor == WHITE) {
            R.color.black
        } else {
            R.color.white
        }
        switch.setTextColor(ContextCompat.getColor(view.context, textColor))
        textView.setTextColor(ContextCompat.getColor(view.context, textColor))
        constraintMain.setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                if (state.mainBackgroundColor == WHITE) {
                    R.color.white
                } else {
                    R.color.black
                }
            )
        )
        textView.text = state.descriptionText
        switch.setOnClickListener {
            state.toggleBackgroundColor()
        }
    }
}