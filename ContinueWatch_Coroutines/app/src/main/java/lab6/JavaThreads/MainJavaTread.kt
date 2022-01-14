package lab6.JavaThreads
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainJavaTread: AppCompatActivity() {
    var secondsElapsed: Int = 0
    private lateinit var prefernce: SharedPreferences
    lateinit var textSecondsElapsed: TextView
    private lateinit var backgroundThread: Thread

    companion object {
        const val TAG = "count"
    }

    private fun stBackgroundThread() {
        backgroundThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                        Log.i(TAG, "$secondsElapsed")
                    }
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    Log.i(TAG, "backgroundThread interrupted")
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefernce = getPreferences(Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main_java_tread)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        Log.i(TAG, "In onCreate")
    }

    override fun onStart() {
        super.onStart()
        stBackgroundThread()
        backgroundThread.start()
        Log.i(TAG, "backgroundThread start in onStart")
    }

    override fun onStop() {
        super.onStop()
        prefernce.edit().putInt(TAG, secondsElapsed).apply()
        backgroundThread.interrupt()
        Log.i(TAG, "Interrupt in onStop")
    }

    override fun onResume() {
        super.onResume()
        secondsElapsed = prefernce.getInt(TAG, 0)
    }
}
