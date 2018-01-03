package fr.iutmindfuck.qcmiutlyon1.data;

public class MarkItem {
    private final String title;
    private final String description;
    private final String average;

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
        if (Float.parseFloat(average) >= 0)
            return average;
        else
            return "N/A";
    }

    public String toString()
    {
        return title + " / " + description + " / " + average;
    }
}
