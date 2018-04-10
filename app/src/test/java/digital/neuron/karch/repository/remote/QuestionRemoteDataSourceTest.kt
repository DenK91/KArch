package digital.neuron.karch.repository.remote

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import digital.neuron.karch.data.Config
import digital.neuron.karch.data.api.QuestionResponse
import digital.neuron.karch.data.api.QuestionService
import digital.neuron.karch.data.model.Question
import digital.neuron.karch.data.repository.remote.QuestionRemoteDataSource
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber

import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock

class QuestionRemoteDataSourceTest {
    @Mock
    private var questionService: QuestionService? = null
    private var remoteDataSource: QuestionRemoteDataSource? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        remoteDataSource = QuestionRemoteDataSource(questionService!!)
    }

    @Test
    fun loadQuestions_ShouldReturnFromRemoteService() {
        val questionResponse = QuestionResponse()
        val subscriber = TestSubscriber<List<Question>>()
        given(questionService!!.loadQuestionsByTag(Config.ANDROID_QUESTION_TAG))
                .willReturn(Flowable.just(questionResponse))

        remoteDataSource!!.loadQuestions(anyBoolean()).subscribe(subscriber)

        then<QuestionService>(questionService).should().loadQuestionsByTag(Config.ANDROID_QUESTION_TAG)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun addQuestion_NoThingToDoWithRemoteService() {
        val question = mock(Question::class.java)
        remoteDataSource!!.addQuestion(question)

        then<QuestionService>(questionService).shouldHaveZeroInteractions()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearData_NoThingToDoWithRemoteService() {
        remoteDataSource!!.clearData()

        then<QuestionService>(questionService).shouldHaveZeroInteractions()
    }
}