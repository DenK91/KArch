package digital.neuron.karch.ui.questions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide

import java.security.InvalidParameterException

import digital.neuron.karch.R
import digital.neuron.karch.data.model.Question
import digital.neuron.karch.ui.base.BaseRecyclerViewAdapter
import digital.neuron.karch.util.DateTimeUtils
import io.reactivex.annotations.NonNull
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_question.*

internal class QuestionAdapter(@param:NonNull private val questions: MutableList<Question>) : BaseRecyclerViewAdapter() {

    inner class QuestionViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(question: Question) {
            val (_, user, creationDate, title) = question
            tvTitle.text = title
            tvUser.text = user!!.name
            tvCreatedTime.text = DateTimeUtils.formatRelativeTime(creationDate)
            Glide.with(ivProfile).load(user.image).into(ivProfile)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): QuestionViewHolder =
            QuestionViewHolder(LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_question, viewGroup, false))

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        super.onBindViewHolder(viewHolder, i)
        (viewHolder as QuestionViewHolder).bind(questions[i])
    }

    override fun getItemCount(): Int = questions.size

    fun replaceData(questions: List<Question>) {
        this.questions.clear()
        this.questions.addAll(questions)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Question {
        if (position < 0 || position >= questions.size) {
            throw InvalidParameterException("Invalid item index")
        }
        return questions[position]
    }

    fun clearData() {
        questions.clear()
        notifyDataSetChanged()
    }
}
