package com.example.unsplashgallery.ui.imagedetails

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.unsplashgallery.R
import com.example.unsplashgallery.databinding.UnsplashImageDetailsFragmentBinding

class UnsplashImageDetailsFragment : Fragment(R.layout.unsplash_image_details_fragment) {

    private val args by navArgs<UnsplashImageDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = UnsplashImageDetailsFragmentBinding.bind(view)

        binding.apply {
            val image = args.image
            Glide.with(this@UnsplashImageDetailsFragment)
                .load(image.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        unsplashImageDetailsProgressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        unsplashImageDetailsProgressBar.isVisible = false
                        unsplashImageDetailsAuthorTextView.isVisible = true
                        unsplashImageDetailsDescriptionTextView.isVisible = image.description != null
                        return false
                    }
                })
                .into(unsplashImageDetailsImageView)

            unsplashImageDetailsDescriptionTextView.text = image.description
            val uri = Uri.parse(image.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            unsplashImageDetailsAuthorTextView.apply {
                text = "Image by ${image.user.name} on Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }
    }

}