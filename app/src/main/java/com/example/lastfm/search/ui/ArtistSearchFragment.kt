package com.example.lastfm.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.lastfm.R

class ArtistSearchFragment : Fragment() {

    companion object {
        fun newInstance() = ArtistSearchFragment()
    }

    private lateinit var viewModel: ArtistSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_artist_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArtistSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}