package iti.mad.marketly.presentation.view.adapter

import androidx.recyclerview.widget.DiffUtil
import iti.mad.marketly.data.model.SmartCollection


class BrandsDiffUtils: DiffUtil.ItemCallback<SmartCollection>()  {
    override fun areItemsTheSame(oldItem: SmartCollection, newItem: SmartCollection): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SmartCollection, newItem: SmartCollection): Boolean {
        return oldItem == newItem
    }
}