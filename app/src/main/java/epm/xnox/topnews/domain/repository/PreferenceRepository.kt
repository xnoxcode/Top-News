package epm.xnox.topnews.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    suspend fun saveDate(date: String)
    fun readDate() : Flow<String>
}