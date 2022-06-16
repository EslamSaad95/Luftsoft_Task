package com.example.luftsofttask.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


private const val PRIMARY = 0


abstract class BaseAdapter<VB : ViewBinding, T> : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    private var  loadingPosition=0
    private var hasMore = false
    private var isError = false
    private var items = listOf<T>()
    private var mutableItems = items.toMutableList<T?>()
    private var filteredList = mutableListOf<T?>()
    private var clickListener: ((clickedView: View, item: T, position: Int) -> Unit)? = null
    private lateinit var parent: ViewGroup


    override fun getItemCount() = mutableItems.size





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.parent = parent
        return MainViewHolder(getMainBinding())
        }


    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseAdapter<VB, T>.MainViewHolder).setContent(mutableItems[position])

    }


    @Suppress("UNCHECKED_CAST")
    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val charString = constraint.toString()
            mutableItems = if (charString.isEmpty()) items.toMutableList() else filteredList

            val filterResults = FilterResults()
            filterResults.values = mutableItems
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            mutableItems = results.values as MutableList<T?>
            notifyDataSetChanged()
        }
    }


    /**
     * Set on view click listener
     * @param clickListener (clickedView, clickedItem, clickedPosition)
     */
    fun setOnClickListener(clickListener: (clickedView: View, item: T, position: Int) -> Unit) {
        this.clickListener = clickListener
    }


    /**
     * Set filtered data
     * @param filteredList
     */
    fun submitFilteredList(filteredList: MutableList<T?>) {
        this.filteredList = filteredList
    }

    /**
     * Get items
     */
    fun getCurrentItems() = items

    /**
     * Get item by position
     * @param position
     */
    fun getItem(position: Int) = mutableItems[position]!!

    /**
     * Replace current items with new items
     * @param items New items to fill
     */
    fun fill(items: List<T>) {
        this.items = items
        mutableItems.clear()
        mutableItems.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * Add items to end of list
     * @param items New items to add
     */
    fun addItems(items: List<T>) {
        if (hasMore)
            mutableItems.addAll(items)
        dismissLoading()

    }

    /**
     * Add item to end of list
     * @param item New item to add
     */
    fun addItem(item: T) {
        mutableItems.add(item)
        notifyDataSetChanged()
    }

    /**
     * Add item to certain position in list
     * @param position Where to add item
     * @param item New item to add
     */
    fun addItem(position: Int, item: T) {
        mutableItems.add(position, item)
        notifyDataSetChanged()
    }

    /**
     * Replace item in certain position
     * @param position Where to add item
     * @param item New item to replace with
     */
    fun replace(position: Int, item: T) {
        mutableItems[position] = item
        notifyDataSetChanged()
    }

    /**
     * Remove item from list
     * @param position Item position
     * @return List size
     */
    fun removeItem(position: Int): Int {
        mutableItems.removeAt(position)
        notifyDataSetChanged()
        return mutableItems.size
    }

    /**
     * Remove all items from list
     */
    fun clear() {
        items = listOf()
        mutableItems.clear()
        notifyDataSetChanged()
    }

    /**
     * Show loading
     */
    fun showLoading() {
        mutableItems.add(null)
        loadingPosition=mutableItems.lastIndex
        notifyDataSetChanged()
    }

    /**
     * Dismiss loading
     */
    fun dismissLoading() {
        if (mutableItems.isNotEmpty()) {
            mutableItems.removeAt(loadingPosition)
            notifyDataSetChanged()
        }
    }

    /**
     * Notify that no more items
     * @param status true if no more items
     */



    /**
     * Dismiss error
     */
    fun dismissError() {
        if (mutableItems.isNotEmpty()) {
            hasMore = true
            isError = false
            mutableItems.removeAt(mutableItems.lastIndex)
            notifyDataSetChanged()
        }
    }


    protected abstract fun setContent(binding: VB, item: T, position: Int)

    /**
     * On view clicked
     * @param view Clicked view
     * @param item Clicked item
     * @param position Clicked position
     */
    protected fun onViewClicked(view: View, item: T, position: Int) {
        clickListener?.invoke(view, item, position)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getMainBinding(): VB {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val method = (superclass.actualTypeArguments[0] as Class<Any>)
            .getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
        return method.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
    }

    inner class MainViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {

        fun setContent(item: T?) {
            if (item != null) setContent(binding, item, adapterPosition)
        }
    }


}