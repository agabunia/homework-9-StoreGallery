package com.example.homework_9_storegallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_9_storegallery.databinding.AllButtonLayoutBinding
import com.example.homework_9_storegallery.databinding.ButtonLayoutBinding

class FilterAdapter(private val listener: MainActivity):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val diffCallBack = object: DiffUtil.ItemCallback<Filter>() {
        override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitFilterData(value: List<Filter>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return when (viewType) {
            ALL_FILTER -> {
                FilterAllButtonViewHolder(AllButtonLayoutBinding.inflate(inflate, parent, false))
            }
            else -> {
                FilterViewHolder(ButtonLayoutBinding.inflate(inflate, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemsViewModel = differ.currentList[position]
        when(itemsViewModel.value) {
            "ALL_FILTER" -> {
                holder as FilterAllButtonViewHolder
                holder.bind(itemsViewModel)
            }
            else -> {
                holder as FilterViewHolder
                holder.bind(itemsViewModel)
            }
        }
    }

    companion object {
        const val ALL_FILTER = 1
        const val REST_FILTER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position].value) {
            "ALL_FILTER" -> ALL_FILTER
            else -> REST_FILTER
        }
    }

    interface FilterItemClickListener {
        fun onBtnClicked(categoryType: String, isActivated: Boolean)
    }

    fun onClick(filter: Filter){
        val categoryType = filter.categoryType
        val isActivated = filter.isActive
        listener.onBtnClicked(categoryType, isActivated)
    }

    inner class FilterViewHolder(private val binding: ButtonLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(filter: Filter) {
            binding.apply {
                tvBtn.text = filter.categoryType
                ivImage.setImageResource(filter.image)
                layoutBtn.setOnClickListener {
                    if(!filter.isActive) {
                        layoutBtn.setBackgroundResource(R.drawable.button_shape_active)
                        filter.isActive = true
                    } else {
                        layoutBtn.setBackgroundResource(R.drawable.button_shape)
                        filter.isActive = false
                    }
                    onClick(filter)
                }
            }
        }
    }

    inner class FilterAllButtonViewHolder(private val binding: AllButtonLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(filter: Filter) {
            binding.apply {
                tvTitle.text = filter.categoryType
                btnLayout.setOnClickListener {
                    for(btn in differ.currentList) {
                        if(btn != filter) {
                            btn.isActive = false
                        }
                    }
                    notifyItemChanged(bindingAdapterPosition)
                    onClick(filter)
                }
            }
        }
    }
}