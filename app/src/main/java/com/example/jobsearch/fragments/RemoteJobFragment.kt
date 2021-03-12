package com.example.jobsearch.fragments


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jobsearch.MainActivity
import com.example.jobsearch.R
import com.example.jobsearch.adapters.RemoteJobAdapter
import com.example.jobsearch.databinding.FragmentRemoteJobBinding
import com.example.jobsearch.utils.Constant
import com.example.jobsearch.viewmodel.RemoteJobViewModel


class RemoteJobFragment : Fragment(R.layout.fragment_remote_job)
       ,SwipeRefreshLayout.OnRefreshListener{

    //TODO 1
    private var _binding  : FragmentRemoteJobBinding?= null
    private val binding get() = _binding!!

    //TODO 4
    private lateinit var viewModel : RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter

    private lateinit var swipeLayout: SwipeRefreshLayout

    //TODO 2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding= FragmentRemoteJobBinding.inflate(inflater, container, false)

        swipeLayout = binding.swipeContainer
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(
                Color.GREEN, Color.RED,
                Color.BLUE, Color.CYAN
        )

        swipeLayout.post {
            swipeLayout.isRefreshing = true
            setUpRecyclerView()
        }
        return binding.root
    }

    //TODO 5
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()

    }

    //TODO 6
    private fun setUpRecyclerView() {

        remoteJobAdapter = RemoteJobAdapter()

        binding.rvRemoteJobs.apply {

            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration( object : DividerItemDecoration(
                    activity, LinearLayout.VERTICAL) {})

            adapter = remoteJobAdapter
        }

        fetchingData()
    }

    //TODO 7
    private fun fetchingData() {
        activity?.let{

            if (Constant.checkInternetConnection(requireActivity())){

                viewModel.remoteJobResult().observe(viewLifecycleOwner, { remoteJob ->
                    if (remoteJob != null) {
                        remoteJobAdapter.differ.submitList(remoteJob.jobs)
                        swipeLayout.isRefreshing = false
                    }
                })
            }else{
                Toast.makeText(activity,"No Internet Connection", Toast.LENGTH_SHORT).show()
                swipeLayout.isRefreshing = false
            }
        }
    }


    //TODO 3
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //for swipe to refresh
    override fun onRefresh() {
        setUpRecyclerView()
    }

}