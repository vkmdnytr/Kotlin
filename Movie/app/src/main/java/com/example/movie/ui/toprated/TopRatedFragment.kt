package com.example.movie.ui.toprated

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
import com.example.movie.ui.upcoming.UpComingAdapter
import kotlinx.android.synthetic.main.fragment_top_rated.*
import kotlinx.android.synthetic.main.fragment_upcoming.*

class TopRatedFragment : Fragment(), OnItemClickListener {

    private var topRatedRecyclerViewAdapter: TopRatedAdapter? = null
    private var topRatedDataSource: List<MovieResultsItem>? = null
    private val topViewModel by lazy {
        ViewModelProviders.of(this).get(TopRatedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).showBottomSheet()

        setHasOptionsMenu(true)

        topRatedRecyclerView.layoutManager = LinearLayoutManager(this.context)
        topRatedRecyclerViewAdapter = TopRatedAdapter(null, this)
        topRatedRecyclerView.adapter = topRatedRecyclerViewAdapter



        observeViewModel()
        topViewModel.getTopRatedListData()
        searchData()

    }


    //SET DATA
    private fun searchData() {

      topRatedSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }


            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (topRatedSearchEditText.text.isNullOrEmpty()) {
                    topViewModel.getTopRatedListData()
                } else {
                    topViewModel.getSearchTopRatedListData(topRatedSearchEditText.text.toString())

                }
            }
        })
    }
    private  fun observeViewModel() {

        topViewModel.getProgressBarStateLiveData()?.observe(this, Observer {
            topRatedProgressBar.visibility = it!!
        })


        topViewModel.getTopRatedLiveData()?.observe(this, Observer {
            when (it) {
                is Results.Success -> {
                    topRatedDataSource=it.data.results
                    handleSuccess(topRatedDataSource)
                }
                is Results.Error -> {
                    handleError()
                }
            }
        })
    }
    private  fun handleSuccess(dataSource: List<MovieResultsItem>?) {
        errorTopRatedTextView.visibility=View.GONE
        topRatedRecyclerView.visibility=View.VISIBLE
        topRatedRecyclerViewAdapter?.updateData(dataSource)
    }
    private  fun handleError() {
        errorTopRatedTextView.visibility=View.VISIBLE
        topRatedRecyclerView.visibility=View.GONE
    }


    //LIST VIEW ITEM CLICK
    override fun onItemClick(id: Int) {
        var bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_topRatedFragment_to_movieDetailFragment, bundle)
    }

    //MENU ITEM
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_top, menu)
    }
    private var menuItem: MenuItem? = null
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuItem = item
        val id = item.itemId
        if (id == R.id.action_most_star) {

            handleSuccess(topRatedDataSource?.sortedByDescending { item -> item.vote_average })

            return true
        }
        if (id == R.id.action_least_star) {

            handleSuccess(topRatedDataSource?.sortedBy { item -> item.vote_average })

            return true
        }
        if (id == R.id.action_no_filter) {

            topRatedDataSource?.let {  handleSuccess(topRatedDataSource) }

            return true
        }


        return super.onOptionsItemSelected(item)
    }

}

