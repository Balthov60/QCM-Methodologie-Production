package fr.iutmindfuck.qcmiutlyon1.data;

import java.io.Serializable;

public class MCQ implements Serializable {

    private final Integer id;
    private final String name;
    private final String description;
    private final boolean isPointNegative;
    private final float coefficient;

    public MCQ(Integer id, String name, String description, boolean isPointNegative, float coefficient) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPointNegative = isPointNegative;
        this.coefficient = coefficient;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public boolean isPointNegative() {
        return isPointNegative;
    }
    public float getCoefficient() {
        return coefficient;
    }

    public String toString()
    {
        return name + " / " + description;
    }

}
