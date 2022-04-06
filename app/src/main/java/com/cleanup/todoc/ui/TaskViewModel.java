package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES

    private final TaskDataRepository taskDataRepository; // taskDataSource

    private final ProjectDataRepository projectDataRepository; // projectDataSource

    private final Executor executor;

    // DATA

    @Nullable

    private LiveData<Project> currentProject;

    public TaskViewModel(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository
            , Executor executor) {

        this.taskDataRepository = taskDataRepository;

        this.projectDataRepository = projectDataRepository;

        this.executor = executor;

    }

    public void init() {
        getAllTask();
    }

    // -------------

    // FOR PROJECT

    // -------------

    public LiveData<Project> getProject() {
        return this.currentProject;
    }

    // -------------

    // FOR TASK

    // -------------

    public LiveData<List<Task>> getAllTask() {

        return taskDataRepository.getAllTask();

    }


    public void createTask( Task task) {

        executor.execute(() -> {

            taskDataRepository.createTask(task);

        });

    }

    public void deleteTask(long taskId) {

        executor.execute(() -> taskDataRepository.deleteTask(taskId));

    }

}
