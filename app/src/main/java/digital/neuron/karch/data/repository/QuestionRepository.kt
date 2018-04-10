package digital.neuron.karch.data.repository

import android.support.annotation.VisibleForTesting
import digital.neuron.karch.data.model.Question
import io.reactivex.Flowable
import java.util.ArrayList
import javax.inject.Inject

class QuestionRepository @Inject constructor(
        @param:Local private val localDataSource: QuestionDataSource,
        @param:Remote private val remoteDataSource: QuestionDataSource)
    : QuestionDataSource {

    @VisibleForTesting
    private var caches: MutableList<Question> = ArrayList()

    override fun loadQuestions(forceRemote: Boolean): Flowable<List<Question>> {
        return if (forceRemote) {
            refreshData()
        } else {
            if (caches.isNotEmpty()) {
                // if cache is available, return it immediately
                Flowable.just(caches.toList())
            } else {
                // else return data from local storage
                localDataSource.loadQuestions(false).take(1)
                        .flatMap { Flowable.fromIterable(it) }
                        .doOnNext { question -> caches.add(question) }
                        .toList().toFlowable()
                        .filter { list -> !list.isEmpty() }
                        .switchIfEmpty(refreshData()) // If local data is empty, fetch from remote source instead.
            }
        }
    }

    /**
     * Fetches data from remote source.
     * Save it into both local database and cache.
     *
     * @return the Flowable of newly fetched data.
     */
    private fun refreshData(): Flowable<List<Question>> {
        return remoteDataSource.loadQuestions(true).doOnNext {
            clearData()
        }.flatMap({ Flowable.fromIterable(it) }).doOnNext { question ->
            caches.add(question)
            localDataSource.addQuestion(question)
        }.toList().toFlowable()
    }

    /**
     * Loads a question by its question id.
     *
     * @param questionId question's id.
     * @return a corresponding question from cache.
     */
    fun getQuestion(questionId: Long): Flowable<Question> {
        return Flowable.fromIterable(caches).filter { question -> question.id == questionId }
    }

    override fun addQuestion(question: Question) {
        //Currently, we do not need this.
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun clearData() {
        caches.clear()
        localDataSource.clearData()
    }
}