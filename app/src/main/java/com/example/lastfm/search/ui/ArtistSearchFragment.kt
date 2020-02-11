package com.example.lastfm.search.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val textChangeListener = object: TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            editable?.toString()?.let { viewModel.newSearch(it) }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

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

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        search_results.layoutManager = linearLayoutManager
        search_results.adapter = adapter

        search_results.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    adapter.onScrolledDown(linearLayoutManager.findLastVisibleItemPosition())
                }
            }
        })

        viewModel.viewData.observe(this.viewLifecycleOwner, Observer { newData ->
            adapter.updateData(newData.results, newData.totalCount, newData.loading)
        })

        adapter.listener = this
    }

    override fun onResume() {
        super.onResume()
        search_field.addTextChangedListener(textChangeListener)
    }

    override fun onPause() {
        search_field.removeTextChangedListener(textChangeListener)
        super.onPause()
    }

    override fun onMoreDataNeeded() {
        viewModel.loadMoreResults()
    }

    override fun onItemClicked(artistId: String) {
        viewModel.goToArtistDetail(artistId)
    }

}