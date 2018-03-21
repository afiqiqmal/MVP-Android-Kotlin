package com.kotlin.mvp.mvpandroid.prefs

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Basyrun Halim
 */

@Module
class PreferenceModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(context: Context): PreferencesRepository {
        return PreferencesRepository(context)
    }

}
