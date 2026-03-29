package com.gully.scanner

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // These will connect to your UI elements
    private lateinit var instructionText: TextView
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // This connects the code to the XML layout you just built
        setContentView(R.layout.activity_main)

        instructionText = findViewById(R.id.instruction_text)
        confirmButton = findViewById(R.id.confirm_button)

        // Initial message for the user
        instructionText.text = "Welcome to PocketUmp. Rotate 360° to begin scanning."
    }
}

