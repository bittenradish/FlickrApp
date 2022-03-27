package de.example.challenge.flickrapp.fragments.childFragments

import android.content.res.Configuration
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.adapter.AdapterItem
import de.example.challenge.flickrapp.adapter.DataAdapter
import de.example.challenge.flickrapp.adapter.OnPhotoItemListener
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.database.RequestHistoryModel
import de.example.challenge.flickrapp.executors.AppExecutors

class SearchingFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    //TODO: replace loading into VM and make it thread save
    private var loadingMore = false
    private var searchingPhoto = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_searching, container, false)
        searchViewModel =
            ViewModelProvider(requireParentFragment()).get(SearchViewModel::class.java)

        val photosRecyclerView: RecyclerView = view.findViewById(R.id.photosRecyclerView)
        photosRecyclerView.layoutManager = GridLayoutManager(
            context, when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 3
                Configuration.ORIENTATION_PORTRAIT -> 2
                else -> 2
            }
        )
        val photosAdapter: DataAdapter = DataAdapter(listOf<AdapterItem>(), OnPhotoItemListener {
            //TODO: Open fullscreen fragment
        })
        photosRecyclerView.adapter = photosAdapter
        searchViewModel.getPhotosLiveData().observe(viewLifecycleOwner, Observer {
            //TODO: improve Adapter update func(don't load all again)
            photosAdapter.notifyDataChanged(it)
            Log.d("SIZE", "AdapterSize: " + photosAdapter.itemCount)
        })
        searchViewModel.getPhotoLoadingMoreLiveData().observe(viewLifecycleOwner, Observer {
            loadingMore = it
        })
        searchViewModel.getPhotoSearchingLiveData().observe(viewLifecycleOwner, Observer {
            searchingPhoto = it
            if (searchingPhoto) {
                //TODO: show progressbar
            } else {
                //TODO: hide progressbar
                //TODO enable search button
            }
        })
        photosRecyclerView.addOnScrollListener(endlessScrolling)
        val searchEditText: EditText = view.findViewById(R.id.searchEditText)
        searchViewModel.getRequestStringLiveData().observe(viewLifecycleOwner, Observer {
            searchEditText.setText(it)
        })
        val searchButton: Button = view.findViewById(R.id.searchButton)
        searchButton.setOnClickListener(View.OnClickListener {
            if (!searchEditText.text.isEmpty()) {
                //TODO disable editText
                //TODO disable search button
                //TODO add to db and search
                searchViewModel.searchFor(searchEditText.text.toString())
                searchEditText.clearFocus()
                AppExecutors.diskIO().execute(Runnable {
                    try {
                        App.getAppInstance().getDataBase().requestDao()
                            .add(RequestHistoryModel(searchEditText.text.toString()))
                    } catch (ex: SQLiteConstraintException) {
                    }
                })

            } else {
                Toast.makeText(context, "Search field is empty", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }

    private val endlessScrolling: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val totalItemsCount = recyclerView.layoutManager?.itemCount
                    val firstVisibleItemPosition =
                        (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (!searchingPhoto && !loadingMore) {
                        //TODO: find the best formula for searching
                        Log.d(
                            "Position",
                            "Position is: " + firstVisibleItemPosition + " total is: " + totalItemsCount
                        )
                        if (firstVisibleItemPosition >= totalItemsCount!! * 0.85) {
                            searchViewModel.loadMore()
                        }
                    }
                }
            }
        }
}