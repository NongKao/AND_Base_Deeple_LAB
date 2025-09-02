package com.projects.base.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projects.base.data.datasource.local.dao.AppInfoDao
import com.projects.base.data.datasource.local.dao.BlockEventDao
import com.projects.base.data.datasource.local.dao.FocusSessionDao
import com.projects.base.data.datasource.local.dao.WallpaperDao
import com.projects.base.data.entity.AppInfoEntity
import com.projects.base.data.entity.BlockEventEntity
import com.projects.base.data.entity.FocusSessionEntity
import com.projects.base.data.entity.WallpaperCacheEntity

@Database(
    entities = [
        AppInfoEntity::class,
        FocusSessionEntity::class,
        BlockEventEntity::class,
        WallpaperCacheEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appInfoDao(): AppInfoDao
    abstract fun focusSessionDao(): FocusSessionDao
    abstract fun blockEventDao(): BlockEventDao
    abstract fun wallpaperDao(): WallpaperDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `focus_sessions` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `intention` TEXT NOT NULL,
                        `startTime` INTEGER NOT NULL,
                        `endTime` INTEGER,
                        `durationMillis` INTEGER,
                        `isCompleted` INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())

                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `block_events` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `package_name` TEXT NOT NULL,
                        `session_type` TEXT NOT NULL,
                        `focus_session_id` INTEGER,
                        `action_taken` TEXT NOT NULL,
                        FOREIGN KEY(`focus_session_id`) REFERENCES `focus_sessions`(`id`) ON DELETE CASCADE
                    )
                """.trimIndent())
                
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_block_events_focus_session_id` ON `block_events` (`focus_session_id`)")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `wallpapers` (
                        `id` TEXT NOT NULL,
                        `categoryType` TEXT NOT NULL,
                        `page` INTEGER NOT NULL,
                        `author` TEXT NOT NULL,
                        `url` TEXT NOT NULL,
                        `downloadUrl` TEXT NOT NULL,
                        `width` INTEGER NOT NULL,
                        `height` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                """.trimIndent())
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_wallpapers_categoryType` ON `wallpapers` (`categoryType`)")
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_wallpapers_category_page` ON `wallpapers` (`categoryType`, `page`)")
            }
        }
    }
}
