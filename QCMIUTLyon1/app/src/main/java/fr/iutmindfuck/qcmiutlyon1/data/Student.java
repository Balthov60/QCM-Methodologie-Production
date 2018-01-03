package fr.iutmindfuck.qcmiutlyon1.data;

import java.io.Serializable;

public class Student implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String group;

    public Student(String id, String firstName, String lastName, String group)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public String getId()
    {
        return id;
    }
    public String getGroup() {
        return group;
    }

    public String toString()
    {
        return firstName + " " + lastName.toUpperCase() + " / " + group;
    }
}
