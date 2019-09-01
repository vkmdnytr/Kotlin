package com.example.movie.ui.toprated

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.common.OnItemClickListener
import com.example.movie.entity.model.data.movie.MovieResultsItem
import com.example.movie.entity.rest.Urls


class TopRatedAdapter(private var mDataSource: List<MovieResultsItem>?,private var onListener:OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(v)

    }

    fun updateData(dataSource: List<MovieResultsItem>?){
        this.mDataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val mockItemViewHolder = holder as MovieViewHolder
        val mockItem = mDataSource?.get(position)
        mockItemViewHolder.subTitle.text=mockItem?.overview
        mockItemViewHolder.title.text=mockItem?.title
        mockItemViewHolder.textReleaseDate.text=mockItem?.release_date
        mockItem?.let {
            mockItemViewHolder.textRate.text=mockItem.vote_average.div(2).toString()
            mockItemViewHolder.rate.rating= mockItem.vote_average.div(2)
        }
        val url = if (Urls.IMAGE_URL != null) "${Urls.IMAGE_URL}${mockItem?.poster_path}" else null
        Glide.with(mockItemViewHolder.movieImageView.context)  //2
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.icon_placeholder)
            .error(R.drawable.icon_placeholder)
            .into(mockItemViewHolder.movieImageView)
        
        mockItemViewHolder.movieItemCoverFrameLayout.setOnClickListener {
            onListener.onItemClick(mockItem?.id?:0)
        }
                  
    }
    override fun getItemCount() = mDataSource?.size ?: 0

    class MovieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textReleaseDate: TextView=itemView.findViewById(R.id.textReleaseDate)
        var subTitle: TextView=itemView.findViewById(R.id.textMovieSubTitle)
        var title: TextView=itemView.findViewById(R.id.textMovieTitle)
        var movieImageView : ImageView=itemView.findViewById(R.id.imageMovie)
        var textRate : TextView=itemView.findViewById(R.id.textRate)
        var rate : RatingBar =itemView.findViewById(R.id.movieRating)
        var movieItemCoverFrameLayout: FrameLayout =itemView.findViewById(R.id.movieItemCoverFrameLayout)
    }
}
