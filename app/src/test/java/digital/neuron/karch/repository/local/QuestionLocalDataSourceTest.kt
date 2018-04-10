package digital.neuron.karch.repository.local

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import digital.neuron.karch.data.database.QuestionDao
import digital.neuron.karch.data.model.Question
import digital.neuron.karch.data.repository.local.QuestionLocalDataSource
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber

import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then

class QuestionLocalDataSourceTest {

    @Mock
    private var questionDao: QuestionDao? = null
    private var localDataSource: QuestionLocalDataSource? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        localDataSource = QuestionLocalDataSource(questionDao!!)
    }

    @Test fun loadQuestions_ShouldReturnFromDatabase() {
        // Given
        val questions = listOf(Question(), Question())
        val subscriber = TestSubscriber<List<Question>>()
        given(questionDao!!.allQuestions).willReturn(Flowable.just(questions))

        // When
        localDataSource!!.loadQuestions(false).subscribe(subscriber)

        // Then
        then<QuestionDao>(questionDao).should().allQuestions
    }

    @Test fun addQuestion_ShouldInsertToDatabase() {
        val question = Question()
        localDataSource!!.addQuestion(question)
        then<QuestionDao>(questionDao).should().insert(question)
    }

    @Test fun clearData_ShouldDeleteAllDataInDatabase() {
        localDataSource!!.clearData()
        then<QuestionDao>(questionDao).should().deleteAll()
    }
}