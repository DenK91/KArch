package digital.neuron.karch.data

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import digital.neuron.karch.data.database.QuestionDao
import digital.neuron.karch.data.database.StackOverflowDb
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Named(DATABASE)
    internal fun provideDatabaseName(): String {
        return Config.DATABASE_NAME
    }

    @Provides
    @Singleton
    internal fun provideStackOverflowDao(context: Context, @Named(DATABASE) databaseName: String): StackOverflowDb {
        return Room.databaseBuilder(context, StackOverflowDb::class.java, databaseName).build()
    }

    @Provides
    @Singleton
    internal fun provideQuestionDao(stackOverflowDb: StackOverflowDb): QuestionDao {
        return stackOverflowDb.questionDao()
    }

    companion object {
        private const val DATABASE = "database_name"
    }
}