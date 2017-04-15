package by.galkina.game.entity;


import java.util.Date;

public class Message {
    private long messageId;
    private Date date;
    private String text;
    private String from;

    public Message() {
    }

    public Message(String text, String from, Date date) {
        this.text = text;
        this.from = from;
        this.date=date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Message(String text) {
        this.text = text;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + messageId +
                "\", \"user\":\"" + from +
                "\", \"date\":\"" + date +
                "\", \"text\":\"" + text +
                "\"}";
    }
}
