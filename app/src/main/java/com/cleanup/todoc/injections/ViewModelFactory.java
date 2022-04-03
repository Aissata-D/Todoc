package com.cleanup.todoc.injections;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.SaveToDocDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory{

    private final TaskDataRepository taskDataRepository; //taskDataSource

    private final ProjectDataRepository projectDataRepository; //projectDataSource

    private final Executor executor;

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context) {

        if (factory == null) {

            synchronized (ViewModelFactory.class) {

                if (factory == null) {

                    factory = new ViewModelFactory(context);

                }

            }

        }

        return factory;

    }

    private ViewModelFactory(Context context) {

        SaveToDocDatabase database = SaveToDocDatabase.getInstance(context);

        this.taskDataRepository = new TaskDataRepository(database.taskDao());

        this.projectDataRepository = new ProjectDataRepository(database.projectDao());

        this.executor = Executors.newSingleThreadExecutor();

    }

    @Override

    @NonNull

    public <T extends ViewModel>  T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(TaskViewModel.class)) {

            return (T) new TaskViewModel(taskDataRepository, projectDataRepository, executor);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
