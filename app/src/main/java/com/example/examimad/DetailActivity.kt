/*
 * FULL NAME: [Insert Your Full Name]
 * STUDENT NUMBER: [Insert Your Student Number]
 * MODULE: IMAD5112 - Introduction to Mobile Application Development
 */

package com.example.examimad

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

/**
 * This screen shows the user a full list of all their gear items.
 * It reads the data from the shared arrays in MainActivity and creates
 * a card for each item using a FOR loop.
 */
class DetailActivity : AppCompatActivity() {

    companion object {
        // Tag used for Android Logging so we can track what the app is doing
        private const val TAG = "DetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load the detail screen layout from the XML file
        setContentView(R.layout.activity_detail)

        Log.d(TAG, "Detail screen just opened. Time to build the checklist...")

        // Create and add cards for every item in the gear list
        populateChecklist()

        // When the user clicks "Back to Base", go back to the main screen
        findViewById<Button>(R.id.btnBackToBase).setOnClickListener {
            Log.d(TAG, "User clicked Back to Base - going back to main screen")
            finish() // This closes this screen and goes back to the previous one
        }
    }

    /**
     * This method builds the checklist by going through each item in the
     * gear arrays using a FOR loop. For every item, it creates a card
     * that shows the item name, category, and extra details.
     */
    private fun populateChecklist() {
        // Find the container where we'll put all the item cards
        val container = findViewById<LinearLayout>(R.id.checklistContainer)
        // Start with a clean slate - remove any cards already there
        container.removeAllViews()

        // Grab the shared data from MainActivity's arrays
        val names = MainActivity.itemNames
        val categories = MainActivity.itemCategories
        val comments = MainActivity.itemComments

        Log.d(TAG, "We have ${names.size} items to show in the checklist")

        // FOR loop: go through each item one at a time and build a card for it
        for (i in names.indices) {
            // Get the details for this particular item
            val name = names[i]
            // Safety check: if the categories array doesn't have this index, just say "Unknown"
            val category = if (i < categories.size) categories[i] else "Unknown"
            // Same safety check for the comments array
            val comment = if (i < comments.size) comments[i] else "No details"

            Log.d(TAG, "Building card number $i: $name (Category: $category)")

            // Create a new CardView to hold this item's info
            val cardView = CardView(this).apply {
                // Set the card size to fill the width and just wrap the content height
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    // Add space at the bottom so cards don't stick together
                    setMargins(0, 0, 0, 12)
                }

                // Give the card a flat background (no rounded corners since we want sharp edges)
                elevation = 0f
                setCardBackgroundColor(
                    ContextCompat.getColor(this@DetailActivity, R.color.cardBackground)
                )
            }

            // Inside the card, create a vertical layout to stack the text on top of each other
            val innerLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(20, 16, 20, 16)
            }

            // Show the item number in a small green text at the top of the card
            val numberBadge = TextView(this).apply {
                text = "Item #$i"
                textSize = 12f
                setTextColor(ContextCompat.getColor(this@DetailActivity, R.color.accentGreen))
            }
            innerLayout.addView(numberBadge)

            // Show the item name in big bold white text
            val nameText = TextView(this).apply {
                text = "• $name"
                textSize = 18f
                setTextColor(ContextCompat.getColor(this@DetailActivity, R.color.whiteText))
                setTypeface(null, Typeface.BOLD)
            }
            innerLayout.addView(nameText)

            // Show the category in smaller light text underneath the name
            val categoryText = TextView(this).apply {
                text = "  Category: $category"
                textSize = 14f
                setTextColor(ContextCompat.getColor(this@DetailActivity, R.color.lightText))
            }
            innerLayout.addView(categoryText)

            // Show the extra comment/details in green text at the bottom
            val commentText = TextView(this).apply {
                text = "  Details: $comment"
                textSize = 14f
                setTextColor(ContextCompat.getColor(this@DetailActivity, R.color.secondaryGreen))
            }
            innerLayout.addView(commentText)

            // Put the inner layout into the card, then add the card to the main container
            cardView.addView(innerLayout)
            container.addView(cardView)
        }

        Log.d(TAG, "All done! We added $names.size cards to the checklist")
    }
}
