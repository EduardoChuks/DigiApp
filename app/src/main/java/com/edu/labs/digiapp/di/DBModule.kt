package com.edu.labs.digiapp.di

import android.app.Application
import androidx.room.Room
import com.edu.labs.digiapp.data.local.ArticlesDB
import com.edu.labs.digiapp.data.local.ArticlesDao
import com.edu.labs.digiapp.data.local.DeletedArticlesDao
import org.koin.dsl.module

val dbModule = module {

    single { provideDataBase(get()) }
    single { provideArticlesDao(get()) }
    single { provideDeletedArticlesDao(get()) }

}

fun provideDataBase(application: Application): ArticlesDB {
    return Room.databaseBuilder(application, ArticlesDB::class.java, "ARTICLESDB")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideArticlesDao(dataBase: ArticlesDB): ArticlesDao {
    return dataBase.articlesDao
}

fun provideDeletedArticlesDao(dataBase: ArticlesDB): DeletedArticlesDao {
    return dataBase.deletedArticlesDao
}