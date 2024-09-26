package epm.xnox.topnews.domain.useCase.database

import epm.xnox.topnews.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetAllArticlesUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke() = repository.getAllArticles()
}