package models;

import lombok.Data;

@Data
public class News {
    private int id;
    private String text;
    private int categoryId;

    public News() {
    }

    public News(int id, String text, int categoryId) {
        this.id = id;
        this.text = text;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
