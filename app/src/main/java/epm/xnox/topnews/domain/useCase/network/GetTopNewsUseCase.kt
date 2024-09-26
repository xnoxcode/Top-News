package epm.xnox.topnews.domain.useCase.network

import epm.xnox.topnews.domain.repository.NetworkRepository
import javax.inject.Inject

class GetTopNewsUseCase @Inject constructor(private val repository: NetworkRepository) {
    suspend operator fun invoke() = repository.getTopNews()
}