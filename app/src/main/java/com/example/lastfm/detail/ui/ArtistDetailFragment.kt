package com.example.lastfm.detail.ui

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lastfm.R
import com.example.lastfm.detail.viewmodel.ArtistDetailViewData
import com.example.lastfm.detail.viewmodel.ArtistDetailViewModel
import com.example.lastfm.detail.viewmodel.ArtistDetailViewModelFactory
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_artist_detail.*
import javax.inject.Inject

class ArtistDetailFragment : Fragment() {

    companion object {
        private const val ARG_ARTIST_ID = "ARG_ARTIST_ID"
        fun getArgumentsBundle(artistId: String): Bundle = bundleOf(
            ARG_ARTIST_ID to artistId
        )
    }

    private lateinit var viewModel: ArtistDetailViewModel

    @Inject
    lateinit var viewModelFactory: ArtistDetailViewModelFactory

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
        viewModel = ViewModelProvider(this, viewModelFactory).get(ArtistDetailViewModel::class.java)

        viewModel.viewData.observe(this.viewLifecycleOwner, Observer {
            renderViewData(it)
        })

        val artistId = requireNotNull(requireArguments().getString(ARG_ARTIST_ID))

        viewModel.loadArtistDetail(artistId)

    }

    private fun renderViewData(viewData: ArtistDetailViewData) {
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE
        artist_detail.visibility = View.GONE
        when(viewData) {
            is ArtistDetailViewData.Loading -> loading_view.visibility = View.VISIBLE
            is ArtistDetailViewData.Error -> {
                error_view.visibility = View.VISIBLE
                error_view.text = viewData.t.message
            }
            is ArtistDetailViewData.Success -> {
                artist_detail.visibility = View.VISIBLE
                val artistDetail = viewData.artist
                artist_name.text = artistDetail.name
                if (!artistDetail.imageUrl.isNullOrEmpty()) {
                    image.visibility = View.VISIBLE
                    Picasso.get().load(artistDetail.imageUrl).into(image)
                } else {
                    image.visibility = View.INVISIBLE
                }
                biography.text = artistDetail.biography ?: ""
                read_more.text = Html.fromHtml(getString(R.string.read_more, artistDetail.webPageUrl))
                read_more.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

}