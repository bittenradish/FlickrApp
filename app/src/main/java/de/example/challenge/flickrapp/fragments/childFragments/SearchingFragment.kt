package de.example.challenge.flickrapp.fragments.childFragments

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.database.RequestHistoryModel
import de.example.challenge.flickrapp.executors.AppExecutors

class SearchingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_searching, container, false)
        //TODO: Implement ViewModel
        val searchEditText: EditText = view.findViewById(R.id.searchEditText)
        val searchButton: Button = view.findViewById(R.id.searchButton)
        searchButton.setOnClickListener(View.OnClickListener {
            AppExecutors.diskIO().execute(Runnable {
                if (!searchEditText.text.isEmpty()) {
                    val toastMessage: String = try {
                        App.getAppInstance().getDataBase().requestDao()
                            .add(RequestHistoryModel(searchEditText.text.toString()))
                        "Added to DB"
                    } catch (ex: SQLiteConstraintException) {
                        "Already exist"
                    }
                    AppExecutors.mainThread().execute(Runnable {
                        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    })
                } else {
                    AppExecutors.mainThread().execute(Runnable {
                        Toast.makeText(context, "Search field is empty", Toast.LENGTH_SHORT).show()
                    })
                }
            })
        })
        return view
    }
}