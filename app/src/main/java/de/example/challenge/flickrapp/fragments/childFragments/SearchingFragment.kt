package de.example.challenge.flickrapp.fragments.childFragments

import android.content.res.Configuration
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
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
import de.example.challenge.flickrapp.adapter.OnItemClickedListener
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.database.RequestHistoryModel
import de.example.challenge.flickrapp.executors.AppExecutors

class SearchingFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_searching, container, false)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        val photosRecyclerView: RecyclerView = view.findViewById(R.id.photosRecyclerView)
        photosRecyclerView.layoutManager = GridLayoutManager(
            context, when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 3
                Configuration.ORIENTATION_PORTRAIT -> 2
                else -> 2
            }
        )
        val photosAdapter: DataAdapter = DataAdapter(listOf<AdapterItem>(), OnItemClickedListener {
            //TODO: Open fullscreen fragment
        })
        photosRecyclerView.adapter = photosAdapter
        searchViewModel.getPhotosLiveData().observe(viewLifecycleOwner, Observer {
            photosAdapter.notifyDataChanged(it)
        })
        val searchEditText: EditText = view.findViewById(R.id.searchEditText)
        val searchButton: Button = view.findViewById(R.id.searchButton)
        searchButton.setOnClickListener(View.OnClickListener {
            if (!searchEditText.text.isEmpty()) {
                //TODO add to db and search
                searchViewModel.searchFor(searchEditText.text.toString())

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
}