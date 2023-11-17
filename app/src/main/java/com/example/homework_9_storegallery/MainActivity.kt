package com.example.homework_9_storegallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_9_storegallery.databinding.ActivityMainBinding
import com.example.homework_9_storegallery.databinding.ButtonLayoutBinding

class MainActivity : AppCompatActivity(), FilterAdapter.FilterItemClickListener {
    private lateinit var binding: ActivityMainBinding
    var filteredList: MutableList<String> = mutableListOf()
    var newClothes: MutableList<Clothes> = mutableListOf()
    var allClothes: MutableList<Clothes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    fun setUp() {
        setFilerRecyclerview()
        setClothesRecyclerView()
        setData()
    }

    fun setData() {
        allClothes = loadClothesData()
        newClothes.addAll(allClothes)
    }

    fun setFilerRecyclerview() {
        val filerRecyclerview = binding.rvFilter
        filerRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val filterAdapter = FilterAdapter(this)
        filterAdapter.submitFilterData(loadFilterData())
        filerRecyclerview.adapter = filterAdapter
    }

    fun setClothesRecyclerView() {
        val clothesRecyclerView = binding.rvClothes
        clothesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        val clothesAdapter = ClothesAdapter()
        clothesAdapter.submitClothesData(newClothes)
        clothesRecyclerView.adapter = clothesAdapter
    }

    override fun onBtnClicked(categoryType: String, isActivated: Boolean) {
        if(categoryType == "All") {
            filteredList.clear()
        } else {
            if(isActivated) {
                filteredList.add(categoryType)
            } else {
                filteredList.remove(categoryType)
            }
        }

        newClothes = filteredClothes(allClothes, filteredList)
        if(newClothes.isEmpty()) newClothes = allClothes
        (binding.rvClothes.adapter as? ClothesAdapter)?.submitClothesData(newClothes)
    }

    fun filteredClothes(loadClothesData: MutableList<Clothes>, filteredList: MutableList<String>): MutableList<Clothes> {
        var result: MutableList<Clothes> = mutableListOf()
        loadClothesData.forEach {
            if (filteredList.contains(it.categoryType)) {
                result.add(it)
            }
        }
        return result
    }

    fun loadFilterData(): MutableList<Filter> {
        val filterData = mutableListOf<Filter>()
        filterData.add(Filter(1, "All", 0,"ALL_FILTER"))
        filterData.add(Filter(2, "Party", R.drawable.party_icon))
        filterData.add(Filter(3, "Camping", R.drawable.camp_icon))
        filterData.add(Filter(4, "Category1", R.drawable.category_icon))
        filterData.add(Filter(5, "Category2", R.drawable.category_icon))
        filterData.add(Filter(6, "Category3",R.drawable.category_icon))
        return filterData
    }

    fun loadClothesData(): MutableList<Clothes> {
        val clothesData = mutableListOf<Clothes>()
        clothesData.add(
            Clothes(1, R.drawable.clothes_1, "Category1 blazer", 120, "Category1")
        )
        clothesData.add(
            Clothes(2, R.drawable.clothes_2, "Camping blazer", 135, "Camping")
        )
        clothesData.add(
            Clothes(3, R.drawable.clothes_3, "Party blazer", 100, "Party")
        )
        clothesData.add(
            Clothes(4, R.drawable.clothes_1, "Category1 blazer", 20, "Category1")
        )
        clothesData.add(
            Clothes(5, R.drawable.clothes_4, "Category2 suit blazer", 90, "Category2")
        )
        clothesData.add(
            Clothes(6, R.drawable.clothes_3, "Party suit blazer", 60, "Party")
        )
        clothesData.add(
            Clothes(7, R.drawable.clothes_2, "Camping suit blazer", 145, "Camping")
        )
        clothesData.add(
            Clothes(8, R.drawable.clothes_1, "Category1 suit blazer", 120, "Category1")
        )
        clothesData.add(
            Clothes(9, R.drawable.clothes_4, "Category3 suit blazer", 120, "Category3")
        )
        return clothesData
    }
}