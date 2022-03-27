package de.example.challenge.flickrapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.flickrapi.models.PhotoModel

class PhotoViewHolder(@NonNull itemView: View) : AbstractViewHolder(itemView) {
    private lateinit var itemImageView: ImageView
    private lateinit var titleTextView: TextView

    init {
        itemImageView = itemView.findViewById(R.id.itemImageView)
        titleTextView = itemView.findViewById(R.id.itemTitleTextView)
    }

    override fun bind(item: AdapterItem, onClickedAction: OnItemClickedListener) {
        val photoItem: PhotoModel = item as PhotoModel
        titleTextView.text = photoItem.title
        Glide
            .with(itemView)
            .load(photoItem.getPhotoUrl())
            .error(R.drawable.ic_launcher_foreground)
            .into(itemImageView)

        itemView.setOnClickListener {
            onClickedAction.onItemClicked()
        }
    }
}