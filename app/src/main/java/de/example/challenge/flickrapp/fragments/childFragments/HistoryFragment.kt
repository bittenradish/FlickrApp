package de.example.challenge.flickrapp.fragments.childFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.adapter.AdapterItem
import de.example.challenge.flickrapp.adapter.DataAdapter
import de.example.challenge.flickrapp.adapter.OnHistoryItemListener

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        searchViewModel =
            ViewModelProvider(requireParentFragment()).get(SearchViewModel::class.java)
        val historyAdapter: DataAdapter =
            DataAdapter(listOf<AdapterItem>(), object : OnHistoryItemListener {
                override fun onItemClicked(requestText: String) {
                    //TODO: add action -> { open searchFragment and start search}
                    searchViewModel.searchFor(requestText)
                }

                override fun deleteButtonClicked(requestText: String) {
                    historyViewModel.deleteRequest(requestText)
                }
            })
        val historyRecyclerView: RecyclerView = view.findViewById(R.id.historyRecyclerView)
        historyRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        historyViewModel.getRequestsHistory().observe(viewLifecycleOwner, Observer {
            historyAdapter.notifyDataChanged(it)
        })
        historyRecyclerView.adapter = historyAdapter
        view.findViewById<Button>(R.id.clearDbButton).setOnClickListener {
            historyViewModel.clearRequestHistory()
        }
        return view
    }

}