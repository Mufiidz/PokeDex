package id.my.mufidz.pokedex.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<RequestParam, ResponseObject, UseCaseResult> {

    protected abstract suspend fun execute(param: RequestParam?): ResponseObject

    protected abstract suspend fun ResponseObject.transformToResult(): UseCaseResult

    suspend fun getResult(param: RequestParam? = null): UseCaseResult =
        withContext(Dispatchers.IO) {
            execute(param).transformToResult()
        }
}