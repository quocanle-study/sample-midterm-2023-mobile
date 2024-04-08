package com.midterm2023.quocanle.databaseUtil;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.midterm2023.quocanle.DAO.StudentDAO;
import com.midterm2023.quocanle.model.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDAO studentDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, context.getApplicationInfo().dataDir + "/databases/database-student").build();
        }
        return instance;
    }


}
