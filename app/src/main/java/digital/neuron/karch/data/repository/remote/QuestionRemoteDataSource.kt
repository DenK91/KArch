package digital.neuron.karch.data.repository.remote

import digital.neuron.karch.data.Config
import digital.neuron.karch.data.api.QuestionService
import digital.neuron.karch.data.model.Question
import digital.neuron.karch.data.repository.QuestionDataSource
import io.reactivex.Flowable
import javax.inject.Inject

class QuestionRemoteDataSource @Inject
constructor(private val questionService: QuestionService) : QuestionDataSource {

    override fun loadQuestions(forceRemote: Boolean): Flowable<List<Question>> {
        return questionService.loadQuestionsByTag(Config.ANDROID_QUESTION_TAG).map({ it.questions })
    }

    override fun addQuestion(question: Question) {
        //Currently, we do not need this for remote source.
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun clearData() {
        //Currently, we do not need this for remote source.
        throw UnsupportedOperationException("Unsupported operation")
    }
}