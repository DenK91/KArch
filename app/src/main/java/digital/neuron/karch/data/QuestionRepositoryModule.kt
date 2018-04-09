package digital.neuron.karch.data

import dagger.Module
import dagger.Provides
import digital.neuron.karch.data.repository.Local
import digital.neuron.karch.data.repository.QuestionDataSource
import digital.neuron.karch.data.repository.Remote
import digital.neuron.karch.data.repository.local.QuestionLocalDataSource
import digital.neuron.karch.data.repository.remote.QuestionRemoteDataSource
import javax.inject.Singleton

@Module
class QuestionRepositoryModule {

    @Provides
    @Local
    @Singleton
    fun provideLocalDataSource(questionLocalDataSource: QuestionLocalDataSource): QuestionDataSource {
        return questionLocalDataSource
    }

    @Provides
    @Remote
    @Singleton
    fun provideRemoteDataSource(questionRemoteDataSource: QuestionRemoteDataSource): QuestionDataSource {
        return questionRemoteDataSource
    }

}