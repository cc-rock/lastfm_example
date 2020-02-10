package com.example.lastfm.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lastfm.R
import com.example.lastfm.search.viewmodel.ArtistSearchViewModel
import com.example.lastfm.search.viewmodel.ArtistSearchViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_artist_search.*
import javax.inject.Inject

class ArtistSearchFragment : Fragment(), ArtistSearchAdapter.Listener {

    companion object {
        fun newInstance() = ArtistSearchFragment()
    }

    private val adapter = ArtistSearchAdapter()

    @Inject
    lateinit var viewModelFactory: ArtistSearchViewModelFactory

    private lateinit var viewModel: ArtistSearchViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_artist_search, container, false)
    }

    override fun onDestroyView() {
        adapter.listener = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ArtistSearchViewModel::class.java)

        search_results.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        search_results.adapter = adapter

        search_field.doAfterTextChanged { viewModel.newSearch(it?.toString() ?: "") }

        viewModel.viewData.observe(this.viewLifecycleOwner, Observer { newData ->
            adapter.updateData(newData.results, newData.totalCount)
        })

        adapter.listener = this
    }

    override fun onMoreDataNeeded() {
        viewModel.loadMoreResults()
    }

}