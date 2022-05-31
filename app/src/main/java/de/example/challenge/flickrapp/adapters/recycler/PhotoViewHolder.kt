package de.example.challenge.flickrapp.adapters.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.data.repository.flickrapi.models.PhotoModel

class PhotoViewHolder(@NonNull itemView: View) : AbstractViewHolder(itemView) {
    private var itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
    private var titleTextView: TextView = itemView.findViewById(R.id.itemTitleTextView)

    override fun bind(@NonNull item: AdapterItem, @NonNull onClickedAction: OnItemClickedListener) {
        val onPhotoItemListener: OnPhotoItemListener = onClickedAction as OnPhotoItemListener
        val photoItem: PhotoModel = item as PhotoModel
        titleTextView.text = photoItem.title
        Glide
            .with(itemView)
            .load(photoItem.getPhotoUrl())
            .error(R.drawable.ic_flickr_foreground)
            .into(itemImageView)

        itemView.setOnClickListener {
            //TODO: implement and get correct position
            onPhotoItemListener.onItemClicked(1)
        }
    }
}