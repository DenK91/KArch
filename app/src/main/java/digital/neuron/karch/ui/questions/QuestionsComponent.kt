package digital.neuron.karch.ui.questions

import dagger.Component
import digital.neuron.karch.data.QuestionRepositoryComponent
import digital.neuron.karch.ui.base.ActivityScope
import digital.neuron.karch.util.schedulers.SchedulerModule

@ActivityScope
@Component(modules = [QuestionsPresenterModule::class, SchedulerModule::class],
        dependencies = [QuestionRepositoryComponent::class])
interface QuestionsComponent {
    fun inject(questionsActivity: QuestionsActivity)
}