package com.example.movie.ui.detail

import DetailResultItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.common.LocalFavoriteHelper
import com.example.movie.entity.model.data.favorite.LocalFavoriteMovie
import com.example.movie.entity.model.sealed.Results
import com.example.movie.entity.rest.Urls
import com.example.movie.ui.MainActivity
import com.example.movie.ui.upcoming.UpComingViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*


class MovieDetailFragment : Fragment() {

    private var movieId: Int? = null
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
    }
    private var localDataSource:DetailResultItem?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).hideBottomSheet()

        movieId= arguments?.getInt("id")

        observeViewModel()
        movieId?.let {movieId->
            viewModel.getMovieDetailData(movieId)
        }


        addFavoriteMovie.setOnClickListener {
            localDataSource?.let { dataSource->
                if(!LocalFavoriteHelper.isExist(dataSource.id.toString())){
                    LocalFavoriteHelper.addFavoriteToLocal(LocalFavoriteMovie(dataSource.poster_path,dataSource.overview,dataSource.id,dataSource.vote_count,dataSource.video,dataSource.title,dataSource.vote_average.toFloat(),dataSource.release_date))
                    addFavoriteMovie.setImageResource(android.R.drawable.btn_star_big_on)
                }else{
                    LocalFavoriteHelper.removeFavoriteToLocal(LocalFavoriteMovie(dataSource.poster_path,dataSource.overview,dataSource.id,dataSource.vote_count,dataSource.video,dataSource.title,dataSource.vote_average.toFloat(),dataSource.release_date))
                    addFavoriteMovie.setImageResource(android.R.drawable.btn_star_big_off)
                }
            }


        }

    }


    private fun observeViewModel() {

        viewModel.getProgressBarStateLiveData().observe(this, Observer {
            detailProgressBar.visibility=it!!
        })


        viewModel.getMovieDetailLiveData().observe(this, Observer {
            when (it) {
                is Results.Success -> {
                    handleSuccess(it.data)
                }
                is Results.Error -> {
                    handleError()
                }
            }
        })
    }
    private fun handleSuccess(dataSource:DetailResultItem) {
        localDataSource=dataSource
        detailTitleTextView.text=dataSource.original_title
        detailSubTitleTextView.text=dataSource.tagline
        detailOverViewTextView.text=dataSource.overview
        detailDateTextView.text=dataSource.release_date
        detailCompanyTextView.text=dataSource.production_companies[0].name
        if(!LocalFavoriteHelper.isExist(dataSource.id.toString())){
          addFavoriteMovie.setImageResource(android.R.drawable.btn_star_big_off)
        }else{
           addFavoriteMovie.setImageResource(android.R.drawable.btn_star_big_on)
        }
        val url = if (Urls.IMAGE_URL != null) "${Urls.IMAGE_URL}${dataSource?.poster_path}" else null
        Glide.with(this.context!!)
            .load(url)
            .centerCrop()
            .placeholder(R.mipmap.icon_movie)
            .error(R.mipmap.icon_black)
            .into(detailImageView)
        detailCoverConstraintLayout.visibility=View.VISIBLE
        errorDetailTextView.visibility=View.GONE
    }
    private fun handleError() {
        detailCoverConstraintLayout.visibility=View.GONE
        errorDetailTextView.visibility=View.VISIBLE
    }

}
