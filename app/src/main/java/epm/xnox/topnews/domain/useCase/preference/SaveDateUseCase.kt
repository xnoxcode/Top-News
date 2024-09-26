package epm.xnox.topnews.domain.useCase.preference

import epm.xnox.topnews.domain.repository.PreferenceRepository
import javax.inject.Inject

class SaveDateUseCase @Inject constructor(private val repository: PreferenceRepository) {
    suspend operator fun invoke(date: String) = repository.saveDate(date)
}