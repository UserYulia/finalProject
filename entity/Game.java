package by.galkina.game.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Game {
    private long gameId;
    private User user;
    private Date date;
    private boolean win;
    private BigDecimal bet;


    public Game(long gameId, User user, boolean win, BigDecimal bet, Date date) {
        this.gameId = gameId;
        this.user = user;
        this.win = win;
        this.bet = bet;
        this.date = date;
    }

    public Game(User user, Date date, boolean win, BigDecimal bet) {
        this.user = user;
        this.date = date;
        this.win = win;
        this.bet = bet;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public BigDecimal getBet() {
        return bet;
    }

    public void setBet(BigDecimal bet) {
        this.bet = bet;
    }

    @Override
    public String toString() {
        return  "{\"id\":\"" + gameId +
                "\", \"date\":\"" + date +
                "\", \"win\":\"" + win +
                "\", \"bet\":" + bet +
                '}';
    }
}
