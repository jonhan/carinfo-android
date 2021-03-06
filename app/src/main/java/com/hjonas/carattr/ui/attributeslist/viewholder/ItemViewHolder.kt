package com.hjonas.carattr.ui.attributeslist.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.hjonas.carattr.ui.attributeslist.CarAttributeItem

abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  abstract fun bind(data: CarAttributeItem)
}
