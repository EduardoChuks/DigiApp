package com.edu.labs.digiapp

import android.content.Context
import com.edu.labs.digiapp.data.remote.api.HomeAPIs
import com.edu.labs.digiapp.di.appModule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit

class RetrofitClientTest : KoinTest {

    private val retrofit: Retrofit by inject()

    @Mock
    private lateinit var context: Context

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        startKoin {
            androidContext(context)
            modules(appModule)
        }
    }

    @Test
    fun testRetrofitInstance() {
        assert(retrofit.baseUrl().toUrl().toString() == BuildConfig.BASE_API_URL)
    }

    @Test
    fun testPlacesService() = runBlocking {
        val service = retrofit.create(HomeAPIs::class.java)
        try {
            val response = service.recentlyArticles()
            val responseBody = response?.hits
            assert(response != null)
            assert(!responseBody.isNullOrEmpty())
        } catch (e: Exception) {
            e.printStackTrace()
            error(e.localizedMessage ?: "error")
        }
    }

}