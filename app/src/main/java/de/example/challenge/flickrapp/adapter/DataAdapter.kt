package de.example.challenge.flickrapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import de.example.challenge.flickrapp.R

class DataAdapter(
    @NonNull mData: List<AdapterItem>,
    @NonNull itemClickedAction: OnItemClickedListener
) : RecyclerView.Adapter<AbstractViewHolder>() {

    private var onItemClickedListener: OnItemClickedListener = itemClickedAction
    private var adapterData: List<AdapterItem> = mData

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AbstractViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            1 -> HistoryViewHolder(layoutInflater.inflate(R.layout.history_item, viewGroup, false))
            //TODO: write option for search ViewHolder
            else -> HistoryViewHolder(
                layoutInflater.inflate(
                    R.layout.photo_search_item,
                    viewGroup,
                    false
                )
            )
        }
    }

    fun notifyDataChanged(list: List<AdapterItem>) {
        this.adapterData = list
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(abstractViewHolder: AbstractViewHolder, position: Int) {
        abstractViewHolder.bind(adapterData[position], onItemClickedListener)
    }

    override fun getItemCount(): Int {
        return adapterData.size
    }

    override fun getItemViewType(position: Int): Int {
        return adapterData[position].getItemType()
    }
}