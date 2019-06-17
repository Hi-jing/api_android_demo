package com.dhj.demo.entity;

/**
 * 课程实体
 *
 * @author denghaijing
 */
public class Course {

    private Integer id;

    private String name;

    private String teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", teacher=" + teacher + "]";
    }

}
