package fr.iutmindfuck.qcmiutlyon1.data;

import java.io.Serializable;

public class Student implements Serializable {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String group;

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
        String subGroup = group.substring(3);
        return subGroup.toUpperCase();
    }

    public String toString()
    {
        return lastName.toUpperCase() + " " + firstName;
    }
}
