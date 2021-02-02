package com.afkoders.sample

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.afkoders.chatoyantview.R
import com.afkoders.chatoyantview.chatoyant_view.ChatoyantStyleable
import com.afkoders.chatoyantview.databinding.ActivityExampleWithCardBinding


class SampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExampleWithCardBinding
    private lateinit var listOfAllChatoyantViews: ArrayList<ChatoyantStyleable>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleWithCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            listOfAllChatoyantViews = arrayListOf(
                csChatoyantBottomExample,
                tvTitleInner,
                chatoyantButton,
                csChatoyant,
                tvTitle,
                csChatoyantHeart
            )
        }

        binding.seekBarAngle.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                listOfAllChatoyantViews.forEach { it.setAngle(progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.seekBarShineWidth.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                listOfAllChatoyantViews.forEach { it.setShineWidth(progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.seekBarShineSpeed.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                listOfAllChatoyantViews.forEach { it.setShineSpeed(progress) }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.cbIsRelativeToScreen.setOnCheckedChangeListener { buttonView, isChecked ->
            listOfAllChatoyantViews.forEach { it.setRelativeToScreen(isChecked) }
        }

        binding.cbBitmapBackground.setOnCheckedChangeListener { buttonView, isChecked ->
            listOfAllChatoyantViews.forEach {
                it.setBitmapForBackground(
                    if (isChecked) {
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.chato_template
                        )
                    } else {
                        null
                    }
                )
            }
        }

        // This overrides the radiogroup onCheckListener
        binding.radioGroupMode.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state

            listOfAllChatoyantViews.forEach {
                it.setRepeatMode(
                    when (group.findViewById<View>(checkedId) as RadioButton) {
                        binding.modeClamp -> 0
                        binding.modeRepeat -> 1
                        else -> 2
                    }
                )
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.csChatoyantBottomExample.clipFromViews(binding.cardInner)
    }
}