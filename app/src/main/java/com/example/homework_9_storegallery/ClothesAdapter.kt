package com.example.homework_9_storegallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_9_storegallery.databinding.ClothesLayoutBinding
import java.util.zip.Inflater

class ClothesAdapter : RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<Clothes>() {
        override fun areItemsTheSame(oldItem: Clothes, newItem: Clothes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Clothes, newItem: Clothes): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitClothesData(value: List<Clothes>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothesViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ClothesViewHolder(ClothesLayoutBinding.inflate(inflate, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ClothesViewHolder, position: Int) {
        val itemsModelView = differ.currentList[position]
        holder.bind(itemsModelView)
    }

    inner class ClothesViewHolder(private val binding: ClothesLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cloth: Clothes) {
            binding.apply {
                tvClothesTitle.text = cloth.title
                tvClothesPrice.text = "\$ ${cloth.price}"
                ivClothesImage.setImageResource(cloth.image)
            }
        }
    }
}