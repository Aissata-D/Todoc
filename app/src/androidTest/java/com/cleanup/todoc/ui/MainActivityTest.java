package com.cleanup.todoc.ui;


import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    /*
    private TaskDao taskDao;
    private TestDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        taskDao = db.getUserDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        Task task = TestUtil.createUser(3);
        user.setName("george");
        userDao.insert(user);
        List<User> byName = userDao.findUsersByName("george");
        assertThat(byName.get(0), equalTo(user));
    }

 */
}