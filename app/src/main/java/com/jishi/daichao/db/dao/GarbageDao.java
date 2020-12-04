package com.jishi.daichao.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jishi.daichao.db.entity.GarbageEntity;

import java.util.List;

@Dao
public interface GarbageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(GarbageEntity entity);

    @Delete
    void deleteData(GarbageEntity entity);

    @Query("select * from garbage  where submit_time == :submitTime")
    GarbageEntity findBySubmitTime(String submitTime);

    @Query("delete from garbage")
    void deleteAllData();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateData(GarbageEntity entity);

    @Query("select * from garbage  ")
    List<GarbageEntity> getAllData();

    @Query("Select * from garbage where id>:theId Order By id")
    List<GarbageEntity> getAfterIdData(long theId);

    @Query("DELETE from garbage")
    int deleteAll();
}
