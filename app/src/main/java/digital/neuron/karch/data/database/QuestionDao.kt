package digital.neuron.karch.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

import digital.neuron.karch.data.Config
import digital.neuron.karch.data.model.Question
import io.reactivex.Flowable

@Dao
interface QuestionDao {
    @get:Query("SELECT * FROM " + Config.QUESTION_TABLE_NAME)
    val allQuestions: Flowable<List<Question>>

    @Query("SELECT * FROM " + Config.QUESTION_TABLE_NAME + " WHERE id == :id")
    fun getQuestionById(id: Int): Flowable<Question>

    @Insert(onConflict = REPLACE)
    fun insert(question: Question)

    @Query("DELETE FROM " + Config.QUESTION_TABLE_NAME)
    fun deleteAll()
}
