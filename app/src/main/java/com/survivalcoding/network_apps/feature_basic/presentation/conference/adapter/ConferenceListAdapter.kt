package com.survivalcoding.network_apps.feature_basic.presentation.conference.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.network_apps.databinding.ConferenceListItemBinding
import com.survivalcoding.network_apps.feature_basic.domain.model.ConferenceItem
import com.survivalcoding.network_apps.feature_basic.presentation.conference.ConferenceItemViewHolder

class ConferenceListAdapter(private val onLinkClicked: (String) -> Any) :
    ListAdapter<ConferenceItem, ConferenceItemViewHolder>(ConferenceDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConferenceItemViewHolder {
        val binding =
            ConferenceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConferenceItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConferenceItemViewHolder, position: Int) {
        holder.bind(getItem(position), onLinkClicked)
    }
}