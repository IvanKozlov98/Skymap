package itmo.skymap.navigator.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import itmo.skymap.R

class SensorsActivity : FragmentActivity()   {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)
    }
}
