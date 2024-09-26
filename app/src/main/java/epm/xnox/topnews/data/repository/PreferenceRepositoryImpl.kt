package epm.xnox.topnews.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import epm.xnox.topnews.core.common.Constant
import epm.xnox.topnews.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = Constant.PREFERENCE_NAME)

private object PreferencesKeys {
    val DATE_NEWS = stringPreferencesKey(name = Constant.DATE_NEWS)
}

class PreferenceRepositoryImpl @Inject constructor(private val context: Context) : PreferenceRepository {
    override suspend fun saveDate(date: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.DATE_NEWS] = date
        }
    }

    override fun readDate(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.DATE_NEWS] ?: SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(Calendar.getInstance().time)
        }
    }
}