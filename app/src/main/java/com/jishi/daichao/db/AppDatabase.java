package com.jishi.daichao.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jishi.daichao.db.dao.GarbageDao;
import com.jishi.daichao.db.entity.GarbageEntity;
import com.jishi.daichao.widget.Constant;

import io.reactivex.annotations.NonNull;

/**
 * @author Noway
 * @date 2020/5/19
 * @desc
 */
@Database(entities = {
        GarbageEntity.class,
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GarbageDao getGarbageDao();

    private static volatile AppDatabase sInstance;

    public static AppDatabase getDatabase(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance =
                            Room.databaseBuilder(context, AppDatabase.class, Constant.DB_NAME)
                                    .addCallback(new Callback() {
                                        //第一次创建数据库时调用，但是在创建所有表之后调用的
                                        @Override
                                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                            super.onCreate(db);
                                        }

                                        //当数据库被打开时调用
                                        @Override
                                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                            super.onOpen(db);
                                        }
                                    })
                                    .allowMainThreadQueries()//允许在主线程查询数据
//                                    .addMigrations(MIGRATION_2_3)//迁移数据库使用
                                    .fallbackToDestructiveMigration()//迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃但是会清数据
                                    .build();
                }
            }
        }

        return sInstance;
    }

    public static void onDestroy() {
        sInstance = null;
    }

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //此处对于数据库中的所有更新都需要写下面的代码
            database.execSQL("CREATE TABLE IF NOT EXISTS `log` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 0," +
                    "`type` TEXT,`data` TEXT,`isSend` TEXT,`sendTime` TEXT,`upDateTime` TEXT)");

        }
    };
}
