package com.cleanup.todoc;

import static junit.framework.TestCase.assertEquals;
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


    // FOR DATA TESTING

    private static long PROJECT_ID = 5L;
    private static long TASK_ID = 55L;
    private static long TASK_ID_2 = 56L;
    private static Task TASK_DEMO = new Task(TASK_ID, PROJECT_ID, "Test BDD TASK", new Date().getTime());
    private static Task TASK_DEMO_2 = new Task(TASK_ID_2, PROJECT_ID, "Test BDD TASK", new Date().getTime());

    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Test BDD Project", 0xFFA3CED2);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private SaveToDocDatabase database;

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

    @Test
    public void insertAndGetProject() throws InterruptedException {

        // BEFORE : Adding a new Project
        this.database.projectDao().createProject(PROJECT_DEMO);

        // TEST Insert Project and Get project
        // Get PROJECT_DEMO
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {

        // BEFORE : Adding a new task and his project
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);

        // TEST Insert  Task
        // Get TASK_DEMO
        Task task = LiveDataTestUtil.getValue(this.database.taskDao().getTask(TASK_ID));
        assertTrue(task.getName().equals(TASK_DEMO.getName()) && task.getId() == TASK_ID);

    }
    @Test
    public void GetAllTask() throws InterruptedException {

        // BEFORE : Adding 2 tasks and his project
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO_2);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());

        //Verifie that existe 2 task in a database
        assertEquals(2,tasks.size());


    }

    @Test
    public void DeleteTask() throws InterruptedException {
        // BEFORE : Adding 2 tasks and his project
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO_2);

        //Delete the second task whit id = TASK_DEMO_2
        this.database.taskDao().deleteTask(TASK_ID_2);
        Task task1 = LiveDataTestUtil.getValue(this.database.taskDao().getTask(TASK_ID));
        Task task2 = LiveDataTestUtil.getValue(this.database.taskDao().getTask(TASK_ID_2));
        //verified that task1 exist
        assertTrue(task1.getName().equals(TASK_DEMO.getName()) && task1.getId() == TASK_ID);
        //verified that task2 does not exist
        assertEquals(null,task2);

    }
}
