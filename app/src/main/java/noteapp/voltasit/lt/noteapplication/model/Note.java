package noteapp.voltasit.lt.noteapplication.model;

/**
 * Created by Romas Noreika on 2017-12-17.
 */

public class Note {
    private String id;
    private long categoryId;
    private String title;
    private String content;
    private String userEmail;


    public Note(String objectId, String title, String content) {
        this.id = objectId;
        this.title = title;
        this.content = content;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
