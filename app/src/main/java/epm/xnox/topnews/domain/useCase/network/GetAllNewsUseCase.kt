package epm.xnox.topnews.domain.useCase.network

import epm.xnox.topnews.domain.repository.NetworkRepository
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(private val repository: NetworkRepository) {
    suspend operator fun invoke(world: String, date: String) = repository.getAllNews(world, date)
}