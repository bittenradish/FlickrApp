package de.example.challenge.flickrapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import de.example.challenge.flickrapp.fragments.FlowFragment

class MainActivity : AppCompatActivity() {

    private lateinit var flowFragment: FlowFragment
    private val savedFlowFragmentKey = "SAVED_FLOW_FRAGMENT_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            flowFragment = FlowFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.mainFrameLayout, flowFragment)
                .commit()
        } else {
            flowFragment =
                supportFragmentManager.getFragment(
                    savedInstanceState,
                    savedFlowFragmentKey
                ) as FlowFragment
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, savedFlowFragmentKey, flowFragment)
    }

    override fun onBackPressed() {
        Log.d("TAG", "flow " + flowFragment)
        if (flowFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }
}