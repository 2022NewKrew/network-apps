package com.survivalcoding.network_apps.feature_conference.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.network_apps.databinding.ConferenceListItemBinding
import com.survivalcoding.network_apps.feature_conference.domain.model.ConferenceItem

class ConferenceItemViewHolder(private val binding: ConferenceListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(conferenceItem: ConferenceItem, onLinkClicked: (String) -> Any) {
        binding.textViewItemDescription.text = conferenceItem.toString()
        binding.textViewItemLink.text = conferenceItem.link
        binding.textViewItemLink.setOnClickListener {
            onLinkClicked(conferenceItem.link)
        }
    }
}