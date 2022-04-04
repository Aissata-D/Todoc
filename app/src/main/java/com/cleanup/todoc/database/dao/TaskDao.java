package com.cleanup.todoc.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    //Get all Task like a LiveData in a database
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTask();

    //Get all Task like a LiveData
    @Query("SELECT * FROM Task WHERE id = :id")
    LiveData<Task> getTask(long id);

    //Get all Task like a Cursur
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    Cursor getTaskWithCursor(long projectId);

    // Insert a new Task
    @Insert
    long insertTask(Task task);

    //Update a Task
    @Update
    int updateTask(Task task);

    //Delete a Task
    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);


}
