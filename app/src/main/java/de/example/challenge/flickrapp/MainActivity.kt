package de.example.challenge.flickrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import de.example.challenge.flickrapp.fragments.FlowFragment

class MainActivity : AppCompatActivity() {

    private var flowFragment: FlowFragment? = null
    private val savedFlowFragmentKey = "SAVED_FLOW_FRAGMENT_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            flowFragment = FlowFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.mainFrameLayout, flowFragment!!)
                .commit()
        } else {
            flowFragment =
                supportFragmentManager.getFragment(
                    savedInstanceState,
                    savedFlowFragmentKey
                ) as FlowFragment?
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        flowFragment?.let { supportFragmentManager.putFragment(outState, savedFlowFragmentKey, it) }
    }

    override fun onBackPressed() {
        Log.d("TAG", "flow " + flowFragment)
        if (flowFragment?.onBackPressed() == true) {
            super.onBackPressed()
        }
    }
}