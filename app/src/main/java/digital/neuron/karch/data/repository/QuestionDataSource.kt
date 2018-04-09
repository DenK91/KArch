package digital.neuron.karch.data.repository

import digital.neuron.karch.data.model.Question
import io.reactivex.Flowable

interface QuestionDataSource {
    fun loadQuestions(forceRemote: Boolean): Flowable<List<Question>>

    fun addQuestion(question: Question)

    fun clearData()
}
