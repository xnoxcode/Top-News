package epm.xnox.topnews.domain.useCase.database

import epm.xnox.topnews.domain.mappers.toEntity
import epm.xnox.topnews.domain.model.Article
import epm.xnox.topnews.domain.repository.DatabaseRepository
import javax.inject.Inject

class InsertArticleUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(article: Article) = repository.insertArticle(article.toEntity())
}