package fr.iutmindfuck.qcmiutlyon1.data;

public class MCQ {

    private String id;
    private String name;
    private String description;
    private String type;
    private float coef;

    public MCQ(String id, String name, String description, String type, float coef) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.coef = coef;
    }

    public String getId() {
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

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setCoef(float coef) {
        this.coef = coef;
    }
}
