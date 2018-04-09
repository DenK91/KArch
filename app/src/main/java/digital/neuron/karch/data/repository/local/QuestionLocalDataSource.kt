package digital.neuron.karch.data.repository.local

import digital.neuron.karch.data.database.QuestionDao
import digital.neuron.karch.data.model.Question
import digital.neuron.karch.data.repository.QuestionDataSource
import io.reactivex.Flowable
import javax.inject.Inject

class QuestionLocalDataSource @Inject
constructor(private val questionDao: QuestionDao) : QuestionDataSource {

    override fun loadQuestions(forceRemote: Boolean): Flowable<List<Question>> {
        return questionDao.allQuestions
    }

    override fun addQuestion(question: Question) {
        // Insert new one
        questionDao.insert(question)
    }

    override fun clearData() {
        // Clear old data
        questionDao.deleteAll()
    }
}