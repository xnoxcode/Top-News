package epm.xnox.topnews.domain.useCase.preference

import epm.xnox.topnews.domain.repository.PreferenceRepository
import javax.inject.Inject

class ReadDateUseCase @Inject constructor(private val repository: PreferenceRepository) {
    operator fun invoke() = repository.readDate()
}