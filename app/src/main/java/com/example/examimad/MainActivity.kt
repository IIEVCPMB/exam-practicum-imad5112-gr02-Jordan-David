/*
 * FULL NAME: [Insert Your Full Name]
 * STUDENT NUMBER: [Insert Your Student Number]
 * MODULE: IMAD5112 - Introduction to Mobile Application Development
 */

package com.example.examimad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * This is the main screen of the Campsite Commander app.
 * It shows how many items you have packed and lets you add new gear.
 * The gear data is stored in three parallel arrays so they all line up by index.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        // This tag is used for Android's Log system so we can see what's happening
        private const val TAG = "MainActivity"

        /**
         * These three arrays hold all our gear data. They are "parallel arrays"
         * meaning at index 0 we have: "Tent" (name), "Shelter" (category), "4-person waterproof" (comment).
         * We put them in companion object so they are shared between screens.
         */
        val itemNames = mutableListOf("Tent", "Marshmallows", "Flashlight")
        val itemCategories = mutableListOf("Shelter", "Food", "First Aid")
        val itemComments = mutableListOf("4-person waterproof", "Mega size for S'mores", "Check AA batteries")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load the main screen layout from the XML file
        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity just started up. Setting up the screen now...")

        // Connect both buttons on the screen to their actions
        setupClickListeners()

        // Go through the gear list and count how many items there are, then show the number
        updateTotalPackedCount()
    }

    /**
     * This method connects the two buttons on the main screen to their actions.
     * One opens the Add Gear dialog, the other goes to the detailed checklist.
     */
    private fun setupClickListeners() {
        // When the user clicks "Add Gear", open a popup dialog with text fields
        findViewById<MaterialButton>(R.id.btnAddGear).setOnClickListener {
            Log.d(TAG, "User tapped the Add Gear button - showing the input form")
            showAddGearDialog()
        }

        // When the user clicks "View Detailed Checklist", switch to the detail screen
        findViewById<MaterialButton>(R.id.btnViewChecklist).setOnClickListener {
            Log.d(TAG, "User tapped View Checklist - moving to the detail screen")
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * This counts how many items are in the gear list using a FOR loop.
     * It goes through each slot in the itemNames array and counts 1 for each item.
     * Then it updates the big number on the screen.
     *
     * This is done with a loop to meet the assignment requirements.
     */
    private fun updateTotalPackedCount() {
        // Start counting from zero
        var count = 0
        // FOR loop: look at each item in the list, one by one, and add 1 to the count
        for (i in itemNames.indices) {
            // Each index means we found one more item that's packed
            count++
        }

        // Show the final count in the TextView on screen
        val totalCountView = findViewById<TextView>(R.id.totalCount)
        totalCountView.text = count.toString()

        Log.d(TAG, "We counted $count items in the gear list using a FOR loop")
    }

    /**
     * This opens a dialog box where the user can type in new gear details.
     * After they fill in the fields and tap Add, the new item gets added to the arrays.
     * If they leave any field blank, a message pops up telling them to fill it in.
     */
    private fun showAddGearDialog() {
        Log.d(TAG, "Opening the Add Gear popup dialog...")

        // Load the dialog layout XML and find the three text input fields inside it
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_gear, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.inputItemName)
        val categoryInput = dialogView.findViewById<EditText>(R.id.inputCategory)
        val commentInput = dialogView.findViewById<EditText>(R.id.inputComment)

        // Build and show the dialog with Add and Cancel buttons
        AlertDialog.Builder(this)
            .setTitle("Add New Gear")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                // Grab what the user typed and remove any extra spaces at the start/end
                val name = nameInput.text.toString().trim()
                val category = categoryInput.text.toString().trim()
                val comment = commentInput.text.toString().trim()

                Log.d(TAG, "User typed - Name: '$name', Category: '$category', Comment: '$comment'")

                // Check if the user left any fields empty
                if (name.isEmpty() || category.isEmpty() || comment.isEmpty()) {
                    // If something is blank, warn the user with a toast message
                    Log.d(TAG, "Oops! One or more fields are still empty")
                    Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
                } else {
                    // All fields are filled! Add the new item to all three arrays
                    itemNames.add(name)
                    itemCategories.add(category)
                    itemComments.add(comment)

                    Log.d(TAG, "New item added to the list: '$name'")
                    Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show()

                    // Recalculate the total count since we just added one more item
                    updateTotalPackedCount()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    /**
     * When the user comes back from the Detail screen, refresh the count
     * just in case they added items while on that screen.
     */
    override fun onResume() {
        super.onResume()
        updateTotalPackedCount()
    }
}
