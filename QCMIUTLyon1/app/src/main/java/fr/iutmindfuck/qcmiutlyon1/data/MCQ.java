package fr.iutmindfuck.qcmiutlyon1.data;

import java.io.Serializable;

public class MCQ implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private boolean isPointNegative;
    private float coefficient;

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
}
