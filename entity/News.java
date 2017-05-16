package by.galkina.game.entity;


public class News {
    private long newsId;
    private String title;
    private String text;

    public News(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public News() {
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
