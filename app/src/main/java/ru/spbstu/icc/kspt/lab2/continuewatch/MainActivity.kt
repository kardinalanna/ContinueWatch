package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var c = 0;
    var start = false
    lateinit var textSecondsElapsed: TextView

    companion object{
        const val TAG = "count"
    }

    var backgroundThread = Thread {
        while (true) {
            if (start) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                    Log.i(TAG, "$secondsElapsed")
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        Log.i(TAG, "onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAG, secondsElapsed)
        c = outState.getInt(TAG)
        Log.i(TAG, "onSave")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt(TAG);
        Log.i(TAG, "onRestore")
    }

    override fun onStart() {
        super.onStart()
        secondsElapsed = c;
        start = true
    }

    override fun onStop() {
        super.onStop()
        start = false
        c = secondsElapsed
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}