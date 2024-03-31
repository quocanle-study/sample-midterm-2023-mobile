package com.midterm2023.quocanle.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.midterm2023.quocanle.model.Student;

import java.util.List;

@Dao
public interface StudentDAO {
    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("SELECT * FROM student WHERE studentId IN (:studentIds)")
    List<Student> loadAllByIds(int[] studentIds);

    @Query("SELECT * FROM student WHERE fullName LIKE :name LIMIT 1")
    Student findByName(String name);

    @Insert
    void insertAll(Student... students);

    @Query("UPDATE student SET fullName = :name, age = :age, address = :address WHERE studentId = :id")
    void updateStudent(int id, String name, String age, String address);

    @Delete
    void delete(Student student);
}
