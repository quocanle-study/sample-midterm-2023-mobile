package com.midterm2023.quocanle.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Class này dùng để lưu thông tin của sinh viên
@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int studentId;
    @ColumnInfo
    private String fullName;
    @ColumnInfo
    private String age;
    @ColumnInfo
    private String address;

    public Student(String fullName, String age, String address) {
        this.fullName = fullName;
        this.age = age;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
