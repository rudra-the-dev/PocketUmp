import com.google.ar.core.Session
import com.google.ar.core.Config
import com.google.ar.core.ArCoreApk
import android.widget.Toast
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var mSession: Session? = null
    private lateinit var instructionText: TextView
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instructionText = findViewById(R.id.instruction_text)
        confirmButton = findViewById(R.id.confirm_button)

        // Step 7a: Request Camera Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) 
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }
    }

    override fun onResume() {
        super.onResume()
        // Step 7b: Initialize ARCore Session
        if (mSession == null) {
            if (ArCoreApk.getInstance().requestInstall(this, true) == 
                ArCoreApk.InstallStatus.INSTALLED) {
                mSession = Session(this)
                val config = Config(mSession)
                config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
                // We enable Depth for better "Blind Spot" detection
                config.depthMode = Config.DepthMode.AUTOMATIC
                mSession!!.configure(config)
                mSession!!.resume()
            }
        }
    }
}
