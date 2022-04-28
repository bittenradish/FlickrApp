package de.example.challenge.flickrapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.fragments.childFragments.HistoryFragment
import de.example.challenge.flickrapp.fragments.childFragments.search.SearchState
import de.example.challenge.flickrapp.fragments.childFragments.search.SearchViewModel
import de.example.challenge.flickrapp.fragments.childFragments.search.SearchingFragment
import de.example.challenge.flickrapp.ui.SelectableButton

class FlowFragment : Fragment(), IOnBackPressed {
    private lateinit var fragmentOnTheScreen: ChildFragment
    private val savedInstanceStateKey: String = "savedChildFragment"
    private val savedHistoryFragmentKey: String = "savedHistoryFragment"
    private val savedSearchingFragmentKey: String = "savedSearchFragment"
    private lateinit var historyFragment: HistoryFragment
    private lateinit var searchingFragment: SearchingFragment
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var historyButton: SelectableButton
    private lateinit var searchButton: SelectableButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_flow, container, false)
        searchButton = view.findViewById(R.id.searchButton)
        historyButton = view.findViewById(R.id.historyButton)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.getSearchStateLiveDate().observe(viewLifecycleOwner){
            if (it == SearchState.SEARCHING) {
                replaceSearchingFragment()
            }
        }
        if (savedInstanceState == null) {
            historyFragment = HistoryFragment()
            searchingFragment = SearchingFragment()
            childFragmentManager.beginTransaction()
                .add(R.id.flowFrameLayout, searchingFragment)
                .commit()
            setFragmentOnScreenFlag(ChildFragment.SEARCHING_FRAGMENT)
        } else {
            setFragmentOnScreenFlag((savedInstanceState.getSerializable(savedInstanceStateKey) as ChildFragment?)!!)
            searchingFragment = (childFragmentManager.getFragment(
                savedInstanceState,
                savedSearchingFragmentKey
            ) as SearchingFragment?)!!
            historyFragment = (childFragmentManager.getFragment(
                savedInstanceState,
                savedHistoryFragmentKey
            ) as HistoryFragment?) ?: HistoryFragment()
        }

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
            setFragmentOnScreenFlag(ChildFragment.HISTORY_FRAGMENT)
        }
    }

    private fun setFragmentOnScreenFlag(fragment: ChildFragment) {
        fragmentOnTheScreen = fragment
        when (fragmentOnTheScreen) {
            ChildFragment.HISTORY_FRAGMENT -> {
                historyButton.setSelectedState(true)
                searchButton.setSelectedState(false)
            }
            else -> {
                historyButton.setSelectedState(false)
                searchButton.setSelectedState(true)
            }
        }
    }

    private fun replaceSearchingFragment() {
        if (fragmentOnTheScreen != ChildFragment.SEARCHING_FRAGMENT) {
            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            childFragmentManager.beginTransaction()
                .replace(R.id.flowFrameLayout, searchingFragment)
                .commit()
            setFragmentOnScreenFlag(ChildFragment.SEARCHING_FRAGMENT)
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