package de.example.challenge.flickrapp.fragments.childFragments.search

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.adapters.recycler.AdapterItem
import de.example.challenge.flickrapp.adapters.recycler.DataAdapter
import de.example.challenge.flickrapp.adapters.recycler.OnPhotoItemListener
import de.example.challenge.flickrapp.adapters.spinner.SpinnerAdapter
import de.example.challenge.flickrapp.dialogs.ShowDialogs
import de.example.challenge.flickrapp.flickrapi.ResponseCode
import de.example.challenge.flickrapp.flickrapi.models.SortEnum

class SearchingFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var alertDialog: AlertDialog? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var searchButton: ImageButton
    private lateinit var searchEditText: EditText
    private lateinit var sortSpinner: Spinner
    private lateinit var sortAdapter: SpinnerAdapter
    private lateinit var sortOrderCheckBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_searching, container, false)
        searchViewModel =
            ViewModelProvider(requireParentFragment()).get(SearchViewModel::class.java)
        progressBar = view.findViewById(R.id.progressBar)
        searchEditText = view.findViewById(R.id.searchEditText)
        searchButton = view.findViewById(R.id.searchButton)
        sortOrderCheckBox = view.findViewById(R.id.sortOrderCheckBox)
        sortOrderCheckBox.setButtonDrawable(R.color.transparent)
        sortSpinner = view.findViewById(R.id.sortSpinner)
        sortAdapter = SpinnerAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.sort_options)
        ) { it ->
            sortSpinner.setSelection(it)
            startSearchAction(false)
        }.also {
            sortSpinner.adapter = it
        }
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
        })
        photosRecyclerView.addOnScrollListener(endlessScrolling)

        initObserversListeners()
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
                    //TODO: find the best formula for searching
                    if (firstVisibleItemPosition >= totalItemsCount!! * 0.75) {
                        searchViewModel.loadMore()
                    }
                }
            }
        }

    private fun showErrorMessageDialog(code: ResponseCode) {
        if (code == ResponseCode.RESPONSE_OK) {
            return
        }
        //TODO: Implement all errors (Text of errors needed)
        context?.let {
            alertDialog = ShowDialogs.showTempAlertDialog(
                it, when (code) {
                    ResponseCode.API_UNAVAILABLE -> it.getString(R.string.api_unavailable_message)
                    ResponseCode.INVALID_KEY -> it.getString(R.string.invalid_api_key_message)
                    ResponseCode.SERVICE_UNAVAILABLE -> it.getString(R.string.service_unavailable_message)
                    ResponseCode.OPERATION_FAILED -> it.getString(R.string.operation_failed_message)
                    ResponseCode.BAD_URL -> it.getString(R.string.bad_url_message)
                    ResponseCode.SERVER_UNAVAILABLE -> it.getString(R.string.server_unavailable_message)
                    ResponseCode.BAD_REQUEST -> it.getString(R.string.bad_request_message)
                    ResponseCode.URL_CHANGED -> it.getString(R.string.url_changed_message)
                    ResponseCode.METHOD_NOT_FOUND -> it.getString(R.string.method_not_found)
                    ResponseCode.NO_NETWORK_CONNECTION -> it.getString(R.string.no_internet_connection)
                    ResponseCode.NOTHING_FOUND -> it.getString(R.string.nothing_found_message)
                    else -> it.getString(R.string.unknown_error_message)
                }, "Error"
            )
        }
        searchViewModel.observerGotTheMessage()
    }

    private fun startSearchAction(withMessage:Boolean = true) {
        if (!searchEditText.text.isEmpty()) {
            searchViewModel.searchFor(
                searchEditText.text.toString(),
                getSortTypeSelected()
            )
            searchEditText.clearFocus()
        } else {
            if(withMessage) {
                Toast.makeText(context, "Search field is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initObserversListeners() {
        searchViewModel.getRequestStringLiveData().observe(viewLifecycleOwner, Observer {
            searchEditText.setText(it)
        })
        searchViewModel.getSearchStateLiveDate().observe(viewLifecycleOwner) {
            refreshUiEnable(it)
        }
        searchViewModel.getResponseCodeLiveData().observe(viewLifecycleOwner, Observer {
            showErrorMessageDialog(it)
        })
        searchViewModel.getSortPositionObserver().observe(viewLifecycleOwner) {
            sortAdapter.selectedPosition = when (it) {
                SortEnum.RELEVANCE -> 0
                SortEnum.INTERESTINGNESS_DESC ->1
                SortEnum.INTERESTINGNESS_ASC -> 1
                SortEnum.DATE_TAKEN_DESC -> 2
                SortEnum.DATE_TAKEN_ASC -> 2
                SortEnum.DATE_POSTED_DESC -> 3
                SortEnum.DATE_POSTED_ASC -> 3
                else -> 0
            }
            sortSpinner.setSelection(sortAdapter.selectedPosition)
        }
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    startSearchAction()
                    true
                }
                else -> false
            }
        }
        searchButton.setOnClickListener(View.OnClickListener {
            startSearchAction()
        })
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sortOrderCheckBox.isEnabled = (getSortTypeSelected() != SortEnum.RELEVANCE)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        sortOrderCheckBox.setOnClickListener { startSearchAction(false) }
    }

    private fun refreshUiEnable(state: SearchState){
        if (state == SearchState.SEARCHING) {
            progressBar.visibility = View.VISIBLE
            searchButton.isEnabled = false
            searchEditText.isEnabled = false
            sortSpinner.isEnabled = false
            sortOrderCheckBox.isEnabled = false
        } else {
            progressBar.visibility = View.GONE
            searchButton.isEnabled = true
            searchEditText.isEnabled = true
            sortSpinner.isEnabled = true
            sortOrderCheckBox.isEnabled = (getSortTypeSelected() != SortEnum.RELEVANCE)
        }
    }

    private fun getSortTypeSelected(): SortEnum {
        val arr = resources.getStringArray(R.array.sort_options)
        return when (sortSpinner.selectedItem.toString()) {
            arr[0] -> SortEnum.RELEVANCE
            arr[1] -> {
                if (sortOrderCheckBox.isChecked) SortEnum.INTERESTINGNESS_ASC else SortEnum.INTERESTINGNESS_DESC
            }
            arr[2] -> {
                if (sortOrderCheckBox.isChecked) SortEnum.DATE_TAKEN_ASC else SortEnum.DATE_TAKEN_DESC
            }
            arr[3] -> {
                if (sortOrderCheckBox.isChecked) SortEnum.DATE_POSTED_ASC else SortEnum.DATE_POSTED_DESC
            }
            else -> SortEnum.RELEVANCE
        }
    }

    override fun onDestroyView() {
        alertDialog?.dismiss()
        super.onDestroyView()
    }
}