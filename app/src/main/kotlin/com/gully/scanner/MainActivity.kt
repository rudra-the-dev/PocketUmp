package com.gully.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Config
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private var mSession: Session? = null
    private lateinit var instructionText: TextView
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instructionText = findViewById(R.id.instruction_text)
        confirmButton = findViewById(R.id.confirm_button)

        // Step 1: Check Camera Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) 
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }

        // Step 2: Handle the Confirm Button Click
        confirmButton.setOnClickListener {
            exportStadiumData()
        }
    }

    override fun onResume() {
        super.onResume()
        // Step 3: Start ARCore Session
        if (mSession == null) {
            val availability = ArCoreApk.getInstance().checkAvailability(this)
            if (availability.isSupported) {
                mSession = Session(this)
                val config = Config(mSession)
                config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
                config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
                mSession!!.configure(config)
                mSession!!.resume()
            }
        }
        
        // Start a background loop to update instructions
        Thread {
            while (mSession != null) {
                updateWorkflow()
                Thread.sleep(500) // Check every half second to save battery
            }
        }.start()
    }

    private fun updateWorkflow() {
        val frame = try { mSession?.update() } catch (e: Exception) { null } ?: return
        val allPlanes = mSession?.getAllTrackables(Plane::class.java) ?: return

        val hasFloor = allPlanes.any { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING && it.trackingState == TrackingState.TRACKING }
        val verticalPlanes = allPlanes.filter { it.type == Plane.Type.VERTICAL && it.trackingState == TrackingState.TRACKING }

        runOnUiThread {
            when {
                !hasFloor -> {
                    instructionText.text = "Look at the ground to find the pitch..."
                    confirmButton.isEnabled = false
                }
                verticalPlanes.size < 2 -> {
                    instructionText.text = "Floor found! Now scan the side walls/boundaries."
                    confirmButton.isEnabled = false
                }
                else -> {
                    // Blind spot check: Check if walls have enough length (extentX)
                    val isDeepEnough = verticalPlanes.any { it.extentX > 3.0 }
                    if (!isDeepEnough) {
                        instructionText.text = "Blind spot detected ahead! Walk forward to extend the map."
                        confirmButton.isEnabled = false
                    } else {
                        instructionText.text = "Stadium Mapping Complete! Confirming PocketUmp Arena."
                        confirmButton.isEnabled = true
                    }
                }
            }
        }
    }

    private fun exportStadiumData() {
        val planes = mSession?.getAllTrackables(Plane::class.java) ?: return
        val exportList = planes.filter { it.trackingState == TrackingState.TRACKING }.map {
            mapOf(
                "id" to it.hashCode(),
                "type" to it.type.toString(),
                "center" to floatArrayOf(it.centerPose.tx(), it.centerPose.ty(), it.centerPose.tz()),
                "size_x" to it.extentX,
                "size_z" to it.extentZ
            )
        }

        val json = Gson().toJson(exportList)
        // For now, we show a Toast. Later, this goes to your Render backend.
        Toast.makeText(this, "Stadium Saved! JSON ready for Physics Expert.", Toast.LENGTH_LONG).show()
        println("STADIUM_JSON: $json")
    }

    override fun onPause() {
        super.onPause()
        mSession?.pause()
    }
}
