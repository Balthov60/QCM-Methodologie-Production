package fr.iutmindfuck.qcmiutlyon1.data;

import java.io.Serializable;


public class Student implements Serializable {

    private int id;
    private String firstname;
    private String lastname;
    private String group;

    public Student(int id, String firstname, String lastname, String group)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.group = group;
    }

    public int getId()
    {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getGroup() {
        return group;
    }

    public String toString()
    {
        return firstname + " " + lastname.toUpperCase() + " / " + group;
    }
}
