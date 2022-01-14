package com.example.executionservice
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.concurrent.Future


class MainExecutionService: AppCompatActivity() {
      private lateinit var future: Future<*>
    var secondsElapsed: Int = 0
    private lateinit var prefernce: SharedPreferences
    lateinit var textSecondsElapsed: TextView
      companion object {
        const val TAG = "count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefernce = getPreferences(MODE_PRIVATE)
        setContentView(R.layout.activity_main_execution_service)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        Log.i(TAG, "In onCreate")

    }

    override fun onStop() {
        super.onStop()
        prefernce.edit().putInt(TAG, secondsElapsed).apply()
        Log.i(TAG, "Interrupt in onStop")
        future.cancel(true)
    }

    override fun onResume() {
        super.onResume()
        secondsElapsed = prefernce.getInt(TAG, 0)

    }

    override fun onStart() {
        super.onStart()
        future = (application as ExecutorServiceApplication).executorService.submit {
            try {
                Log.i(TAG, "ExecutorService start")
                while (!Thread.currentThread().isInterrupted) {
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                        Log.i(TAG, "$secondsElapsed")
                    }
                }
            } catch (exeption: InterruptedException) {
                Log.i(TAG, "backgroundThread interrupted")
            }
        }
    }
}
