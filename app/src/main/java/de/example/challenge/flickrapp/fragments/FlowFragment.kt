package de.example.challenge.flickrapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.fragments.childFragments.HistoryFragment
import de.example.challenge.flickrapp.fragments.childFragments.SearchViewModel
import de.example.challenge.flickrapp.fragments.childFragments.SearchingFragment

class FlowFragment : Fragment(), IOnBackPressed {
    private lateinit var fragmentOnTheScreen: ChildFragment
    private val savedInstanceStateKey: String = "savedChildFragment"
    private val savedHistoryFragmentKey: String = "savedHistoryFragment"
    private val savedSearchingFragmentKey: String = "savedSearchFragment"
    private lateinit var historyFragment: HistoryFragment
    private lateinit var searchingFragment: SearchingFragment
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_flow, container, false)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.getPhotoSearchingLiveData().observe(viewLifecycleOwner, Observer {
            //TODO: Delete all logs
            if (it) {
                replaceSearchingFragment()
            }
        })
        if (savedInstanceState == null) {
            historyFragment = HistoryFragment()
            searchingFragment = SearchingFragment()
            childFragmentManager.beginTransaction()
                .add(R.id.flowFrameLayout, searchingFragment)
                .commit()
            fragmentOnTheScreen = ChildFragment.SEARCHING_FRAGMENT
        } else {
            fragmentOnTheScreen =
                (savedInstanceState.getSerializable(savedInstanceStateKey) as ChildFragment?)!!
            searchingFragment = (childFragmentManager.getFragment(
                savedInstanceState,
                savedSearchingFragmentKey
            ) as SearchingFragment?)!!
            historyFragment = (childFragmentManager.getFragment(
                savedInstanceState,
                savedHistoryFragmentKey
            ) as HistoryFragment?) ?: HistoryFragment()
        }
        val searchButton: Button = view.findViewById(R.id.searchButton)
        val historyButton: Button = view.findViewById(R.id.historyButton)

        searchButton.setOnClickListener {
            replaceSearchingFragment()
        }

        historyButton.setOnClickListener {
            replaceHistoryFragment()
        }
        return view
    }

    private fun replaceHistoryFragment() {
        if (fragmentOnTheScreen != ChildFragment.HISTORY_FRAGMENT) {
            childFragmentManager.beginTransaction()
                .replace(R.id.flowFrameLayout, historyFragment)
                .addToBackStack(null)
                .commit()
            fragmentOnTheScreen = ChildFragment.HISTORY_FRAGMENT
        }
    }

    private fun replaceSearchingFragment() {
        if (fragmentOnTheScreen != ChildFragment.SEARCHING_FRAGMENT) {
            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            childFragmentManager.beginTransaction()
                .replace(R.id.flowFrameLayout, searchingFragment)
                .commit()
            fragmentOnTheScreen = ChildFragment.SEARCHING_FRAGMENT
        }
    }

    override fun onBackPressed(): Boolean {
        val bool = fragmentOnTheScreen == ChildFragment.SEARCHING_FRAGMENT
        replaceSearchingFragment()
        return bool
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(savedInstanceStateKey, fragmentOnTheScreen)
        childFragmentManager.putFragment(outState, savedSearchingFragmentKey, searchingFragment)
        if (fragmentOnTheScreen == ChildFragment.HISTORY_FRAGMENT) {
            childFragmentManager.putFragment(outState, savedHistoryFragmentKey, historyFragment)

        }
    }

    internal enum class ChildFragment {
        SEARCHING_FRAGMENT, HISTORY_FRAGMENT
    }
}