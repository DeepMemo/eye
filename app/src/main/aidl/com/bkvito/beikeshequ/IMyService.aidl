package com.bkvito.beikeshequ;
import com.bkvito.beikeshequ.Student;

interface IMyService{
    List<Student> getStudent();
    void addStudent(in Student student);
}
