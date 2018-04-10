package digital.neuron.karch.util

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
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

/**
 * Converts epoch time to relative time span.

 * @return relative time span compared with current. i.e: 5 minutes ago
 */
fun Long.toRelativeTimeSpanString() : String {
    return DateUtils.getRelativeTimeSpanString(this * 1000, System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS).toString()
}