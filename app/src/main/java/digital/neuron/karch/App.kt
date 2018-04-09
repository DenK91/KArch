package digital.neuron.karch

import android.app.Application
import digital.neuron.karch.data.DaggerQuestionRepositoryComponent
import digital.neuron.karch.data.QuestionRepositoryComponent

class App : Application() {

    val component: QuestionRepositoryComponent by lazy {
        DaggerQuestionRepositoryComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.provideQuestionRepository()
    }
}