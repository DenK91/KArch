package digital.neuron.karch.ui.questions

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import digital.neuron.karch.data.model.Question

import javax.inject.Inject

import digital.neuron.karch.data.repository.QuestionRepository
import digital.neuron.karch.util.schedulers.RunOn
import digital.neuron.karch.util.schedulers.SchedulerType
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

/**
 * A presenter with life-cycle aware.
 */
class QuestionsPresenter @Inject constructor(
        private val repository: QuestionRepository,
        private val view: QuestionsContract.View,
        @param:RunOn(SchedulerType.IO) private val ioScheduler: Scheduler,
        @param:RunOn(SchedulerType.UI) private val uiScheduler: Scheduler)
    : QuestionsContract.Presenter, LifecycleObserver {

    private val disposeBag: CompositeDisposable

    init {
        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
        disposeBag = CompositeDisposable()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {
        loadQuestions(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onDetach() {
        // Clean up any no-longer-use resources here
        disposeBag.clear()
    }

    override fun loadQuestions(onlineRequired: Boolean) {
        // Clear old data on view
        view.clearQuestions()

        // Load new one and populate it into view
        val disposable = repository.loadQuestions(onlineRequired)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ handleReturnedData(it) }, { handleError(it) }, { view.stopLoadingIndicator() })
        disposeBag.add(disposable)
    }

    override fun getQuestion(questionId: Long) {
        val disposable = repository.getQuestion(questionId)
                .filter { question -> question != null }
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe { question -> view.showQuestionDetail(question) }
        disposeBag.add(disposable)
    }

    override fun search(questionTitle: String) {
        // Load new one and populate it into view
        val disposable = repository.loadQuestions(false)
                .flatMap({ Flowable.fromIterable(it) })
                .filter { question -> question.title != null }
                .filter { question -> question.title!!.toLowerCase().contains(questionTitle.toLowerCase()) }
                .toList()
                .toFlowable()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe { questions ->
                    if (questions.isEmpty()) {
                        // Clear old data in view
                        view.clearQuestions()
                        // Show notification
                        view.showEmptySearchResult()
                    } else {
                        // Update filtered data
                        view.showQuestions(questions)
                    }
                }

        disposeBag.add(disposable)
    }

    /**
     * Updates view after loading data is completed successfully.
     */
    private fun handleReturnedData(list: List<Question>?) {
        view.stopLoadingIndicator()
        if (list != null && !list.isEmpty()) {
            view.showQuestions(list)
        } else {
            view.showNoDataMessage()
        }
    }

    /**
     * Updates view if there is an error after loading data from repository.
     */
    private fun handleError(error: Throwable) {
        view.stopLoadingIndicator()
        view.showErrorMessage(error.localizedMessage)
    }
}
