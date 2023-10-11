package id.my.mufidz.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.pokedex.data.PokemonRepository
import id.my.mufidz.pokedex.usecase.GetDetailPokemonUseCase
import id.my.mufidz.pokedex.usecase.GetPokemonsUseCase
import id.my.mufidz.pokedex.usecase.SearchPokemonUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesPokemonsUseCase(pokemonRepository: PokemonRepository): GetPokemonsUseCase =
        GetPokemonsUseCase(pokemonRepository)

    @Singleton
    @Provides
    fun providesDetailPokemonUseCase(pokemonRepository: PokemonRepository): GetDetailPokemonUseCase =
        GetDetailPokemonUseCase(pokemonRepository)

    @Singleton
    @Provides
    fun providesSearchPokemonUseCase(pokemonRepository: PokemonRepository): SearchPokemonUseCase =
        SearchPokemonUseCase(pokemonRepository)
}