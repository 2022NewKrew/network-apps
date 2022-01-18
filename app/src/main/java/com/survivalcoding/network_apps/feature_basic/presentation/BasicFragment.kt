package com.survivalcoding.network_apps.feature_basic.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.network_apps.R
import com.survivalcoding.network_apps.feature_basic.presentation.util.BasicViewModelProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasicFragment : Fragment(R.layout.fragment_basic) {
    private val viewModel by viewModels<BasicViewModel> {
        BasicViewModelProvider()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultTextView = view.findViewById<TextView>(R.id.result_text_view)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        viewModel.state.observe(viewLifecycleOwner, { state ->
            progressBar.isVisible = state.isLoading
            state.todo?.let { todo ->
                resultTextView.text = todo.toString()
            }
        })

        collectLatestLifecycleFlow(viewModel.event) { event ->
            when (event) {
                is BasicViewModel.UiEvent.ShowSnackBar -> Snackbar.make(
                    resultTextView,
                    event.message,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    }

}

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, block: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest {
                block(it)
            }
        }
    }
}