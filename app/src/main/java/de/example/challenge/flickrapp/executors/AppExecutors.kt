package de.example.challenge.flickrapp.executors

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object AppExecutors {
    private val mDiskIO: Executor = Executors.newSingleThreadExecutor()
    private val mMainThread: Executor = MainThreadExecutor()
    private val mNetworkIO: Executor = Executors.newFixedThreadPool(3)
    private val mScheduledExecutorService: ScheduledExecutorService =
        Executors.newScheduledThreadPool(1)

    fun diskIO(): Executor {
        return mDiskIO
    }

    fun networkIO(): Executor {
        return mNetworkIO
    }

    fun scheduled(): ScheduledExecutorService {
        return mScheduledExecutorService
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            if (command != null) {
                mainThreadHandler.post(command)
            }
        }

    }
}