package com.example.jobsearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.jobsearch.MainActivity
import com.example.jobsearch.R
import com.example.jobsearch.databinding.FragmentJobDetailsBinding
import com.example.jobsearch.models.FavoriteJob
import com.example.jobsearch.models.Job
import com.example.jobsearch.viewmodel.RemoteJobViewModel
import com.google.android.material.snackbar.Snackbar


class JobDetailsFragment : Fragment(R.layout.fragment_job_details) {
    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentJob: Job
    private lateinit var viewModel: RemoteJobViewModel
    private val args: JobDetailsFragmentArgs by navArgs()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobDetailsBinding.inflate(
                inflater,
                container,
                false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        currentJob = args.job!!

        setUpWebView()

        binding.fabAddFavorite.setOnClickListener {
            addJobToFavorite(view)
        }

    }

    private fun addJobToFavorite(view: View) {
        var favJob= FavoriteJob(0,
            currentJob.candidateRequiredLocation, currentJob.category,
            currentJob.companyLogoUrl, currentJob.companyName, currentJob.description, currentJob.id,
            currentJob.jobType, currentJob.publicationDate, currentJob.salary,
            currentJob.title, currentJob.url
        )
        viewModel.addFavJob(favJob)
        Snackbar.make(view, "Job saved successfully",Snackbar.LENGTH_SHORT).show()

    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}