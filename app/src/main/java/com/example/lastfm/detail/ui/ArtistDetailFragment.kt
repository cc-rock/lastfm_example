package com.example.lastfm.detail.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.lastfm.R
import dagger.android.support.AndroidSupportInjection

class ArtistDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ArtistDetailFragment()
    }

    private lateinit var viewModel: ArtistDetailViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_artist_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArtistDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}