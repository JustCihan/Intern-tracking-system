package Objects;

import java.sql.Date;

public class Mission {
    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private boolean completed;
    private Date deliveryDate;
    private String mentorAnswer;
    private String gorev_content;

    // Constructors
    public Mission(int id, String title, String description, Date dueDate, boolean completed, Date deliveryDate, String mentorAnswer, String gorev_content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.deliveryDate = deliveryDate;
        this.mentorAnswer = mentorAnswer;
        this.gorev_content = gorev_content;
    }

    public Mission() {}

    // Getters and Setters
    public int getId() {
        return id;
    } 

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getMentorAnswer() {
        return mentorAnswer;
    }

    public void setMentorAnswer(String mentorAnswer) {
        this.mentorAnswer = mentorAnswer;
    }

    public String getGorev_content() {
        return gorev_content;
    }

    public void setGorev_content(String gorev_content) {
        this.gorev_content = gorev_content;
    }

    // Methods
    @Override
    public String toString() {
        return String.format("Başlık: %s | Açıklama: %s | Bitiş Tarihi: %s", title, description, dueDate);
    }

    public String getShortenedDescription(int maxLength) {
        if (description.length() <= maxLength) {
            return description;
        } else {
            return description.substring(0, maxLength) + "...";
        }
    }

    public String getFormattedDetails() {
        return String.format("ID: %d\nBaşlık: %s\nAçıklama: %s\nTeslim Tarihi: %s\n",
                             id, title, description, dueDate, mentorAnswer, gorev_content);
    }
}
