package digital.neuron.karch.ui.base

import android.arch.lifecycle.LifecycleRegistry
import android.support.v7.app.AppCompatActivity
import digital.neuron.karch.App

import digital.neuron.karch.data.QuestionRepositoryComponent

open class BaseActivity : AppCompatActivity() {
    private val lifecycleRegistry = LifecycleRegistry(this)

    protected val questionRepositoryComponent: QuestionRepositoryComponent
        get() = (application as App).component

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}