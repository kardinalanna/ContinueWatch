package ru.spbstu.icc.kspt.lab2.continuewatch
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Preference: AppCompatActivity() {
    var secondsElapsed: Int = 0
    var start = false
    private lateinit var prefernce: SharedPreferences
    lateinit var textSecondsElapsed: TextView

    companion object{
        const val TAG = "count"
    }

    var backgroundThread = Thread {
        while (true) {
            if (start){
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                    Log.i(TAG, "$secondsElapsed")
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefernce = getPreferences(Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        Log.i(TAG, "in onCreate")
    }

    override fun onPause() {
        super.onPause()
        start = false
        prefernce.edit().putInt(TAG, secondsElapsed).apply()
        Log.i(TAG, "save in onPause ${prefernce.getInt(TAG, 0)}")
    }
    override fun onResume() {
        super.onResume()
        start = true
        secondsElapsed = prefernce.getInt(TAG, 0)
        Log.i(TAG, "get in onResume ${prefernce.getInt(TAG, 0)}")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "in onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "in onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}