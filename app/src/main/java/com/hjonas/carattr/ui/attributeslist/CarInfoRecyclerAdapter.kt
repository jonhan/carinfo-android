package com.hjonas.carattr.ui.attributeslist

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hjonas.carattr.ui.attributeslist.viewholder.ItemViewHolder
import com.hjonas.carattr.ui.attributeslist.viewholder.ViewHolderFactory

// Factory could be injected through some DI framework
class CarInfoRecyclerAdapter(private val factory: ViewHolderFactory) : RecyclerView.Adapter<ItemViewHolder>() {

  private var items = listOf<CarAttributeItem>()

  fun setItems(items: List<CarAttributeItem>) {
    this.items = items
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val type = AttributeSectionType.values()[viewType]
    return factory.createViewHolder(type, parent)
  }

  override fun getItemViewType(position: Int): Int = items[position].sectionType.ordinal

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])
}
