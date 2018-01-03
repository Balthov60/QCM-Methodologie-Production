package fr.iutmindfuck.qcmiutlyon1.views;

public class MarkItem {
    private String title;
    private String description;
    private String average;

    public MarkItem(String title, String description, String average) {
        this.title = title;
        this.description = description;
        this.average = average;
    }


    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getAverage() {
        return average;
    }

    public String toString()
    {
        return title + " / " + description + " / " + average;
    }
}
