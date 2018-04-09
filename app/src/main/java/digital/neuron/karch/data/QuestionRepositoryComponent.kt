package digital.neuron.karch.data

import dagger.Component
import digital.neuron.karch.AppModule
import digital.neuron.karch.data.repository.QuestionRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [QuestionRepositoryModule::class, AppModule::class,
    ApiServiceModule::class, DatabaseModule::class])
interface QuestionRepositoryComponent {
    fun provideQuestionRepository(): QuestionRepository
}