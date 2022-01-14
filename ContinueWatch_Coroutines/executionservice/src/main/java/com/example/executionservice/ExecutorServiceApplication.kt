package com.example.executionservice

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ExecutorServiceApplication: Application() {
        var executorService: ExecutorService = Executors.newSingleThreadExecutor()
    }