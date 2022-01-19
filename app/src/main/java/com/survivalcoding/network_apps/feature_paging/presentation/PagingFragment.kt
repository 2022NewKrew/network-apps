package com.survivalcoding.network_apps.feature_paging.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.network_apps.MyApp
import com.survivalcoding.network_apps.R
import com.survivalcoding.network_apps.feature_paging.presentation.adapter.PostInfoListAdapter
import com.survivalcoding.network_apps.feature_paging.presentation.util.PostInfoViewModelProvider
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingFragment : Fragment(R.layout.fragment_paging) {
    companion object {
        val REQUEST_KEY = "REQUEST_KEY"
        val BUNDLE_KEY = "BUNDLE_KEY"
    }

    private val viewModel by viewModels<PostInfoViewModel> {
        PostInfoViewModelProvider(
            (requireActivity().application as MyApp).postRepository,
            (requireActivity().application as MyApp).userRepository
        )
    }
    private val adapter = PostInfoListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paging, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.paging_recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                adapter.addLoadStateListener {
                    // 뭔가 찝찝함이 남아있다.
                    if (it.refresh::class == LoadState.Error::class) {
                        Snackbar.make(view, "ERROR!!!", Snackbar.LENGTH_SHORT).show()
                    }
                }
                viewModel.postPagingData
                    .collectLatest {
                        adapter.submitData(it)
                    }
            }
        }
    }
}