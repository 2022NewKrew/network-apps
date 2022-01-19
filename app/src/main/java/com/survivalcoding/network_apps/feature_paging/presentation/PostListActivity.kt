package com.survivalcoding.network_apps.feature_paging.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.survivalcoding.network_apps.databinding.ActivityPostListBinding
import com.survivalcoding.network_apps.feature_paging.presentation.adpater.PostListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostListActivity : AppCompatActivity() {

    private val viewModel: PostListViewModel by viewModels()

    private val binding: ActivityPostListBinding by lazy {
        ActivityPostListBinding.inflate(layoutInflater)
    }

    private val adapter: PostListAdapter by lazy {
        PostListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.postsRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.postListUiState.flowWithLifecycle(lifecycle).collectLatest {
                adapter.submitList(it.postList)
            }
        }
    }
}