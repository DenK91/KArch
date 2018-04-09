package digital.neuron.karch.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View
import digital.neuron.karch.util.onClickListener
import digital.neuron.karch.util.onLongClickListener

abstract class BaseRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemClickListener: ((view: View, position: Int) -> Unit)? = null
    private var itemLongClickListener: ((view: View, position: Int) -> Boolean)? = null

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (itemClickListener != null) {
            viewHolder.onClickListener { v, p -> itemClickListener!!.invoke(v,p) }
        }
        if (itemLongClickListener != null) {
            viewHolder.onLongClickListener { v, p -> itemLongClickListener!!.invoke(v,p) }
        }
    }

    fun itemClickListener(event: (view: View, position: Int) -> Unit) {
        itemClickListener = event
    }

    fun itemLongClickListener(event: (view: View, position: Int) -> Boolean) {
        itemLongClickListener = event
    }
}
