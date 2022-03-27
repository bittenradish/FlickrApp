package de.example.challenge.flickrapp.fragments.childFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.adapter.AdapterItem
import de.example.challenge.flickrapp.adapter.DataAdapter
import de.example.challenge.flickrapp.adapter.OnItemClickedListener

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        val historyAdapter: DataAdapter = DataAdapter(listOf<AdapterItem>(), OnItemClickedListener {
            //TODO: add action -> { open searchFragment and start search}
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