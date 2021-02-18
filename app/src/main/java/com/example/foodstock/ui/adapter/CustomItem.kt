package com.example.foodstock.ui.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class CustomItemTouchHelperCallback( val adapter: ProductListAdapter): ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int =
        makeMovementFlags(ItemTouchHelper.ACTION_STATE_SWIPE or ItemTouchHelper.ACTION_STATE_DRAG,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.switchProductsPosition(viewHolder?.adapterPosition?:0, target?.adapterPosition ?: 0 )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    //override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =  adapter.removeProduct(viewHolder?.adapterPosition ?:0)

}