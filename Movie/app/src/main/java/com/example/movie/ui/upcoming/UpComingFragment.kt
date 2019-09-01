package com.example.movie.ui.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.entity.model.data.movie.MovieResultsItem
import com.example.movie.entity.model.sealed.Results
import kotlinx.android.synthetic.main.fragment_upcoming.*
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.movie.common.OnItemClickListener
import com.example.movie.ui.MainActivity


class UpComingFragment : Fragment(), OnItemClickListener {


    private var upComingRecyclerViewAdapter: UpComingAdapter? = null
    private var upComingDataSource: List<MovieResultsItem>? = null
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(UpComingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).showBottomSheet()

        setHasOptionsMenu(true)

        upComingRecyclerView.layoutManager = LinearLayoutManager(this.context)
        upComingRecyclerViewAdapter = UpComingAdapter(null, this)
        upComingRecyclerView.adapter = upComingRecyclerViewAdapter

        observeViewModel()
        viewModel.getUpComingListData()
        searchData()

    }

    //SET DATA
    private fun searchData() {

        upComingSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }


            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (upComingSearchEditText.text.isNullOrEmpty()) {
                    viewModel.getUpComingListData()
                } else {
                    viewModel.getSearchUpComingListData(upComingSearchEditText.text.toString())

                }
            }
        })
    }
    private fun observeViewModel() {

        viewModel.getProgresBarStateLiveData().observe(this, Observer {
            upComingProgressBar.visibility = it!!
        })


        viewModel.getUpComingLiveData().observe(this, Observer {
            when (it) {
                is Results.Success -> {
                    upComingDataSource = it.data.results
                    handleSuccess(it.data.results)
                }
                is Results.Error -> {
                    handleError()
                }
            }
        })
    }
    private fun handleError() {
        errorUpComingTextView.visibility=View.VISIBLE
        upComingRecyclerView.visibility=View.GONE
    }
    private fun handleSuccess(dataSource: List<MovieResultsItem>?) {
        errorUpComingTextView.visibility=View.GONE
        upComingRecyclerView.visibility=View.VISIBLE
        upComingRecyclerViewAdapter?.updateData(dataSource)
    }

    //LIST VIEW CLICK
    override fun onItemClick(id: Int) {
        var bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_upComingFragment_to_movieDetailFragment, bundle)
    }

    //MENU
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_top, menu)
    }
    var menuItem: MenuItem? = null
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuItem = item
        val id = item.itemId
        if (id == R.id.action_most_star) {

            handleSuccess(upComingDataSource?.sortedByDescending { item -> item.vote_average })

            return true
        }
        if (id == R.id.action_least_star) {

            handleSuccess(upComingDataSource?.sortedBy { item -> item.vote_average })

            return true
        }
        if (id == R.id.action_no_filter) {

            handleSuccess(upComingDataSource)
            return true
        }


        return super.onOptionsItemSelected(item)
    }
}

