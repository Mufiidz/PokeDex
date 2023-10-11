package id.my.mufidz.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.my.mufidz.pokedex.data.local.dao.PokemonDao
import id.my.mufidz.pokedex.model.Pokemon

@Database(
    version = 1, entities = [Pokemon::class]
)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}