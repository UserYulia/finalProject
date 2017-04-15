package by.galkina.game.entity;


import java.math.BigDecimal;

public class User {
    private long userId;
    private String email;
    private String password;
    private Role role;
    private BigDecimal score;
    private String username;
    private boolean ban;
    private Double rating;
    private String photo;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(long userId, String email, BigDecimal score, String username, boolean ban, Double rating) {
        this.userId = userId;
        this.email = email;
        this.score = score;
        this.role = Role.USER;
        this.username = username;
        this.ban = ban;
        this.rating = rating;
    }

    public User(long userId, String email, String password, Role role, BigDecimal score, String username, boolean ban, Double rating, String photo) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.score = score;
        this.username = username;
        this.ban = ban;
        this.rating = rating;
        this.photo = photo;
    }

    public void setId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "{\"email\":\"" + email +
                "\", \"score\":\"" + score +
                "\", \"username\":\"" + username +
                "\", \"ban\":\"" + ban +
                "\", \"rating\":\"" + rating +
                "\"}";
    }
}
