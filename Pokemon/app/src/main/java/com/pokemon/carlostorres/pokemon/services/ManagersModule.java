package com.pokemon.carlostorres.pokemon.services;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is the managers module
 *
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
@Module
public class ManagersModule {

    /** Tag for logs **/
    private static final String TAG = ManagersModule.class.getName();

    /** Context to be injected into dependencies **/
    private final Context mContext;

    /**
     * Managers Module constructor
     *
     * @param context
     *         Application context
     */
    public ManagersModule(Context context) {
        this.mContext = context;
    }

    /**
     * Provides with an instance of {@link DatabaseHelper}
     *
     * @return {@link DatabaseHelper} instance
     */
//    @Provides
//    @Singleton
//    public DatabaseHelper databaseHelper() {
//        return new DatabaseHelper(mContext);
//    }
//
//    /**
//     * Bind of the {@link ICategoriesManager} with its implementation
//     *
//     * @param helper
//     *         DB Helper
//     *
//     * @return Implementation of the Alarms Manager
//     */
//    @Provides
//    @Singleton
//    public ICategoriesManager categoriesManager(DatabaseHelper helper) {
//        try {
//            return new CategoriesManager(helper);
//        } catch (SQLException e) {
//            Log.e(TAG, "An error occurred while creating the instance of the Categories Manager", e);
//        }
//        return null;
//    }
//
//    /**
//     * Bind of the {@link ICategoriesManager} with its implementation
//     *
//     * @param helper
//     *         DB Helper
//     *
//     * @return Implementation of the Alarms Manager
//     */
//    @Provides
//    @Singleton
//    public IAppsManager appsManager(DatabaseHelper helper) {
//        try {
//            return new AppsManager(helper);
//        } catch (SQLException e) {
//            Log.e(TAG, "An error occurred while creating the instance of the Apps Manager", e);
//        }
//        return null;
//    }
}
