package fr.iutmindfuck.qcmiutlyon1.data;

public class MCQ {

    private int id;
    private String name;
    private String description;
    private boolean isPointNegative;
    private float coefficient;

    public MCQ(int id, String name, String description, boolean isPointNegative, float coefficient) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPointNegative = isPointNegative;
        this.coefficient = coefficient;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public boolean getPointNegative() {
        return isPointNegative;
    }
    public float getCoefficient() {
        return coefficient;
    }
}
