package com.zy.jdk8features;

import java.io.Closeable;
import java.io.IOException;
import java.util.Comparator;

public class Students implements Comparable<Students>{

    private String name;
    private int age;
    /*1 man,2 women*/
    private int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Students{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + (sex==1?"男":"女") +
                '}';
    }

    public Students(String name, int age, int sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Students students = (Students) o;

        if (age != students.age) return false;
        if (sex != students.sex) return false;
        return name.equals(students.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        result = 31 * result + sex;
        return result;
    }

    @Override
    public int compareTo(Students o) {
        return o.getAge() > age ? 0 : -1;
    }

    public class StudentComptor implements Comparator<Students>,Closeable{
        @Override
        public int compare(Students o1, Students o2) {
            if(o1.getAge() > o2.getAge()) return 1;
            if(o1.getAge() < o2.getAge()) return -1;
            return 0;
        }

        @Override
        public void close() throws IOException {

        }
    }
}
