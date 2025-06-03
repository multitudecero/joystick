package com.example.joystick

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.joystick.JoystickView.OnMoveListener
import com.google.androidgamesdk.GameActivity


class MainActivity : GameActivity() {
    private var mTextViewAngleLeft: TextView? = null
    private var mTextViewStrengthLeft: TextView? = null

    private var mTextViewAngleRight: TextView? = null
    private var mTextViewStrengthRight: TextView? = null
    private var mTextViewCoordinateRight: TextView? = null

    companion object {
        init {
            System.loadLibrary("joystick")
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUi()
        }
    }

    private fun hideSystemUi() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextViewAngleLeft = findViewById<View?>(R.id.textView_angle_left) as TextView?
        mTextViewStrengthLeft = findViewById<View?>(R.id.textView_strength_left) as TextView?

        val joystickLeft = findViewById<View?>(R.id.joystickView_left) as JoystickView
        joystickLeft.setOnMoveListener(object : OnMoveListener {
            override fun onMove(angle: Int, strength: Int) {
                mTextViewAngleLeft!!.setText(angle.toString() + "°")
                mTextViewStrengthLeft!!.setText(strength.toString() + "%")
            }
        })


        mTextViewAngleRight = findViewById<View?>(R.id.textView_angle_right) as TextView?
        mTextViewStrengthRight = findViewById<View?>(R.id.textView_strength_right) as TextView?
        mTextViewCoordinateRight = findViewById<TextView?>(R.id.textView_coordinate_right)

        val joystickRight = findViewById<View?>(R.id.joystickView_right) as JoystickView
        joystickRight.setOnMoveListener(object : OnMoveListener {
            @SuppressLint("DefaultLocale")
            override fun onMove(angle: Int, strength: Int) {
                mTextViewAngleRight!!.setText(angle.toString() + "°")
                mTextViewStrengthRight!!.setText(strength.toString() + "%")
                mTextViewCoordinateRight!!.setText(
                    String.format(
                        "x%03d:y%03d",
                        joystickRight.getNormalizedX(),
                        joystickRight.getNormalizedY()
                    )
                )
            }
        })
    }
}
