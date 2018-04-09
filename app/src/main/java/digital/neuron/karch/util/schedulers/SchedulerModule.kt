package digital.neuron.karch.util.schedulers

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Provides common Schedulers used by RxJava
 */
@Module
class SchedulerModule {

    @Provides
    @RunOn(SchedulerType.IO)
    internal fun provideIo(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @RunOn(SchedulerType.UI)
    internal fun provideUi(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
