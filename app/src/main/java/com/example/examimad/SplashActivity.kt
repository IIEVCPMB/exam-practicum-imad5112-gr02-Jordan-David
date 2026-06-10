/*
 * FULL NAME: [Insert Your Full Name]
 * STUDENT NUMBER: [Insert Your Student Number]
 * MODULE: IMAD5112 - Introduction to Mobile Application Development
 */

package com.example.examimad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This is the first screen that shows when you open the app.
 * It shows the app name "Campsite Commander" with a campfire icon,
 * waits 3 seconds, then automatically takes you to the main screen.
 */
class SplashActivity : AppCompatActivity() {

    companion object {
        // This tag is used for Android's logging system so we can debug the app
        private const val TAG = "SplashActivity"
        // How long we want the splash screen to show (3 seconds = 3000 milliseconds)
        private const val SPLASH_DELAY_MS = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load the splash screen layout from the XML file
        setContentView(R.layout.activity_splash)

        // Using Coroutines to handle the 3-second wait
        // Coroutines let us pause without freezing the whole app
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "Splash screen is showing. Waiting for 3 seconds...")

            // This line pauses the code here for 3 seconds before moving on
            delay(SPLASH_DELAY_MS)

            // After the 3 seconds are up, we switch to the main screen
            Log.d(TAG, "3 seconds are up! Moving to the main screen...")

            // Create an Intent to tell Android we want to open MainActivity
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            // Close this splash screen so the user can't come back to it
            finish()
        }
    }
}
