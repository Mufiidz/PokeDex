package id.my.mufidz.pokedex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.pokedex.data.PokemonRepository
import id.my.mufidz.pokedex.data.PokemonRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl) : PokemonRepository
}