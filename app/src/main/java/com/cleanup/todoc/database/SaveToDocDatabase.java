package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveToDocDatabase extends RoomDatabase {

        // --- SINGLETON ---

        private static volatile SaveToDocDatabase INSTANCE;

        // --- DAO ---

        public abstract TaskDao taskDao();

        public abstract ProjectDao projectDao();

        // --- INSTANCE ---

        public static SaveToDocDatabase getInstance(Context context) {

            if (INSTANCE == null) {

                synchronized (SaveToDocDatabase.class) {

                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                                SaveToDocDatabase.class, "MyDatabaseToDoc.db")

                                .addCallback(prepopulateDatabase())

                                .build();
                    }

                }

            }

            return INSTANCE;

        }

        private static Callback prepopulateDatabase() {

            return new Callback() {

                @Override

                public void onCreate(@NonNull SupportSQLiteDatabase db) {

                    super.onCreate(db);

                    Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().createProject(new Project(1L, "Projet Test", 0xFFEADAD1)));

                }

            };

        }



}
