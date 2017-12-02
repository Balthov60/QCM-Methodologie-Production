package fr.iutmindfuck.qcmiutlyon1.data;

public class MCQ {

    private int id;
    private String name;
    private String description;
    private String type;
    private float coef;

    public MCQ(int id, String name, String description, String type, float coef) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.coef = coef;
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
    public String getType() {
        return type;
    }
    public float getCoef() {
        return coef;
    }
}
