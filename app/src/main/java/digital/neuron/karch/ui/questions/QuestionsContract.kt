package digital.neuron.karch.ui.questions

import digital.neuron.karch.data.model.Question
import digital.neuron.karch.ui.base.BasePresenter

interface QuestionsContract {
    interface View {
        fun showQuestions(questions: List<Question>)

        fun clearQuestions()

        fun showNoDataMessage()

        fun showErrorMessage(error: String)

        fun showQuestionDetail(question: Question)

        fun stopLoadingIndicator()

        fun showEmptySearchResult()
    }

    interface Presenter : BasePresenter<View> {
        fun loadQuestions(onlineRequired: Boolean)

        fun getQuestion(questionId: Long)

        fun search(questionTitle: String)
    }
}
