package com.edu.labs.digiapp

import android.content.Context
import com.edu.labs.digiapp.di.appModule
import com.edu.labs.digiapp.di.dbModule
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CheckModulesTest : KoinTest {

    @Mock
    private lateinit var context: Context

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun checkAllModules() {
        startKoin {
            androidContext(context)
            modules(
                dbModule,
                appModule
            )
        }.checkModules()
    }

}