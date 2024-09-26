package epm.xnox.topnews.domain.useCase.database

import epm.xnox.topnews.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteArticle(id)
}