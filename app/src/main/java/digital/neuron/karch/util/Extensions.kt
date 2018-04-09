package digital.neuron.karch.util

import android.support.v7.widget.RecyclerView
import android.view.View

fun <T : RecyclerView.ViewHolder> T.onClickListener(event: (view: View, position: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(itemView, adapterPosition)
    }
    return this
}

fun <T : RecyclerView.ViewHolder> T.onLongClickListener(event: (view: View, position: Int) -> Boolean): T {
    itemView.setOnLongClickListener {
        event.invoke(itemView, adapterPosition)
    }
    return this
}