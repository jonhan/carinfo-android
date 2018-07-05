package com.hjonas.carattr.ui.attributeslist

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hjonas.carattr.ui.attributeslist.viewholder.ItemViewHolder
import com.hjonas.carattr.ui.attributeslist.viewholder.ViewHolderFactory

/**
 * Define a data-item that contains type and data
 * Expose a setter for list of items
 * Create factory that produce list of items based on network response
 * Inject viewHolder factory that creates viewHolders per type
 */
class CarInfoRecyclerAdapter(private val factory: ViewHolderFactory) : RecyclerView.Adapter<ItemViewHolder>() {

  private var items = listOf<CarInfoItem>()

  fun setItems(items: List<CarInfoItem>) {
    this.items = items
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val type = AttributeSectionType.values()[viewType]
    return factory.createViewHolder(type, parent)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])
}
