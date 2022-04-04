package com.cleanup.todoc;

import static junit.framework.TestCase.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.SaveToDocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectAndTaskDaoTest {


        // FOR DATA

        private SaveToDocDatabase database;

        @Rule

        public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

        @Before

        public void initDb() throws Exception {

            this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),

                    SaveToDocDatabase.class)

                    .allowMainThreadQueries()

                    .build();

        }

        @After

        public void closeDb() throws Exception {

            database.close();

        }

        //TEST
        private static long PROJECT_ID = 5;

        private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Test BDD Project",0xFFA3CED2);
        private static Task TASK_DEMO = new Task(55, PROJECT_ID,"Test BDD TASK",new Date().getTime());

        @Test

        public void insertProject() throws InterruptedException {

                // BEFORE : Adding a new task

                this.database.projectDao().createProject(PROJECT_DEMO);
                this.database.taskDao().insertTask(TASK_DEMO);

                // TEST
                // Get PROJECT_DEMO

                Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject (PROJECT_ID));
               Task task = LiveDataTestUtil.getValue(this.database.taskDao().getTask (55));

                assertTrue(task.getName().equals(TASK_DEMO.getName()) && task.getId() == 55);

                assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);

        }
}
