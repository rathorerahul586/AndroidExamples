package com.rathoreapps.seekBar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rathoreapps.mpchartexample.R
import kotlinx.android.synthetic.main.activity_seek_bar.*
import me.tankery.lib.circularseekbar.CircularSeekBar


class SeekBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seek_bar)

        seekBar.setOnSeekBarChangeListener(object :
            CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar,
                progress: Float,
                fromUser: Boolean
            ) {
                progress_text.text = progress.toInt().toString()
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar) {

            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar) {

            }
        })
    }
}