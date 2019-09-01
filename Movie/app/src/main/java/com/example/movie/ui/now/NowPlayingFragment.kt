package com.example.movie.ui.now

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.common.OnItemClickListener
import com.example.movie.entity.model.data.movie.MovieResultsItem
import com.example.movie.entity.model.sealed.Results
import com.example.movie.ui.MainActivity
import com.example.movie.ui.toprated.TopRatedAdapter
import com.example.nowPlaying.ui.home.NowPlayingViewModel
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.android.synthetic.main.fragment_top_rated.*


class NowPlayingFragment : Fragment() ,OnItemClickListener{

    private var nowPlayingDataSource: List<MovieResultsItem>? = null
    private val nowPlayViewModel by lazy {
        ViewModelProviders.of(this).get(NowPlayingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).showBottomSheet()

        setHasOptionsMenu(true)
        
        nowPlayingRecyclerView.layoutManager = LinearLayoutManager(this.context)

        observeViewModel()
        nowPlayViewModel.getNowPlayData()
        searchData()
    }

    //SET DATA
    private fun searchData() {

       nowPlayingSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }


            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (nowPlayingSearchEditText.text.isNullOrEmpty()) {
                    nowPlayViewModel.getNowPlayData()
                } else {
                    nowPlayViewModel.getSearchNowPlayingListData(nowPlayingSearchEditText.text.toString())

                }
            }
        })
    }
    private fun observeViewModel() {

        nowPlayViewModel.getProgresBarStateLiveData().observe(this, Observer {
            nowPlayingProgressBar.visibility=it!!
        })


        nowPlayViewModel.getNowPlayingLiveData().observe(this, Observer {
            when (it) {
                is Results.Success -> {
                    nowPlayingDataSource=it.data.results
                    handleSuccess(nowPlayingDataSource!!)
                }
                is Results.Error -> {
                    handleError()
                }
            }
        })
    }
    private fun handleSuccess(dataSource: List<MovieResultsItem>) {
        errorNowPlayTextView.visibility=View.GONE
        nowPlayingRecyclerView.visibility=View.VISIBLE
        nowPlayingRecyclerView.adapter=NowPlayingAdapter(dataSource,this)
    }
    private  fun handleError() {
        errorNowPlayTextView.visibility=View.VISIBLE
        nowPlayingRecyclerView.visibility=View.GONE
    }

    //LIST ITEM CLICK
    override fun onItemClick(id: Int) {
        var bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_nowPlayingFragment_to_movieDetailFragment, bundle)
    }

    //MENU ITEM
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_top, menu)
    }
    var menuItem: MenuItem?=null
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuItem = item
        val id=item.itemId
        if (id == R.id.action_most_star) {

            nowPlayingDataSource?.sortedByDescending { item-> item.vote_average }?.let {
                handleSuccess(
                    it
                )
            }

            return true
        }
        if (id == R.id.action_least_star) {

            nowPlayingDataSource?.sortedBy { item-> item.vote_average }?.let { handleSuccess(it) }

            return true
        }
        if (id == R.id.action_no_filter) {

            nowPlayingDataSource?.let { handleSuccess(it) }
            return true
        }


        return super.onOptionsItemSelected(item)
    }
}
