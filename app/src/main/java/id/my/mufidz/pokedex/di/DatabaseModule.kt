package id.my.mufidz.pokedex.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.pokedex.data.local.PokeDatabase
import id.my.mufidz.pokedex.data.local.dao.PokemonDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME: String = "pokedex"

    @Provides
    @Singleton
    fun providesPokeDatabase(application: Application): PokeDatabase =
        Room.databaseBuilder(application, PokeDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesPokemonDao(pokeDatabase: PokeDatabase): PokemonDao = pokeDatabase.pokemonDao()
}