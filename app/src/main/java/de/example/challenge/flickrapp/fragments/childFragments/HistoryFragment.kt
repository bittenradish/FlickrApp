package de.example.challenge.flickrapp.fragments.childFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.adapter.AdapterItem
import de.example.challenge.flickrapp.adapter.DataAdapter
import de.example.challenge.flickrapp.adapter.OnItemClickedListener
import de.example.challenge.flickrapp.application.App

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyViewModel = HistoryViewModel(App.getAppInstance())
        val historyAdapter: DataAdapter = DataAdapter(listOf<AdapterItem>(), OnItemClickedListener {
            //TODO: Add action
        })
        val historyRecyclerView: RecyclerView = view.findViewById(R.id.historyRecyclerView)
        historyRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        historyViewModel.getRequestsHistory().observe(viewLifecycleOwner, Observer {
            historyAdapter.notifyDataChanged(it)
        })
        historyRecyclerView.adapter = historyAdapter
        return view
    }

}