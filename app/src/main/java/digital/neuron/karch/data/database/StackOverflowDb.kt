package digital.neuron.karch.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import digital.neuron.karch.data.model.Question

@Database(entities = [(Question::class)], version = 1)
abstract class StackOverflowDb : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}
