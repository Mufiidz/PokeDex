package id.my.mufidz.pokedex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.my.mufidz.pokedex.model.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<Pokemon>)

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset")
    fun getPokemonsPaged(offset: Int, limit: Int = 20) : List<Pokemon>

    @Query("SELECT * FROM pokemon ORDER BY name ASC LIMIT :limit OFFSET :offset")
    fun getPokemonsPagedASC(offset: Int, limit: Int = 20) : List<Pokemon>

    @Query("SELECT * FROM pokemon ORDER BY name DESC LIMIT :limit OFFSET :offset")
    fun getPokemonsPagedDESC(offset: Int, limit: Int = 20) : List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE name LIKE :query")
    fun searchPokemon(query: String) : List<Pokemon>

}