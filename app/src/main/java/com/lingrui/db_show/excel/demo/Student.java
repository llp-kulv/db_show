package com.lingrui.db_show.excel.demo;

/**
 * Created by Administrator on 2017/4/13.
 */

public class Student {

    String name;
    String sex;
    String age;
    String id;
    String classNo;
    String math;
    String english;
    String chinese;
    String other;

    public Student(String name, String sex, String age, String id, String classNo,
                   String math, String english, String chinese, String other) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.id = id;
        this.classNo = classNo;
        this.math = math;
        this.english = english;
        this.chinese = chinese;
        this.other = other;
    }
}
