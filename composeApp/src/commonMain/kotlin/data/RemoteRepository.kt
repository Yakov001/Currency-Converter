package data

import data.characters.Character
import data.ktor.KtorInstance

interface RemoteRepository {

    suspend fun getCharacters(
        page: Int? = null,
        name: String? = null
    ) : List<Character>

}

class RemoteRepositoryImpl(
    private val ktorInstance: KtorInstance
) : RemoteRepository {

    override suspend fun getCharacters(page: Int?, name: String?): List<Character> {
        return ktorInstance.getCharacters(page, name)
    }

}