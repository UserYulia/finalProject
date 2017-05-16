package by.galkina.game.jdbc;

import by.galkina.game.exception.ConnectionPoolException;
import by.galkina.game.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

   public final static Logger LOG = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static AtomicBoolean isCreated = new AtomicBoolean();
    private static Lock lock = new ReentrantLock();
    private BlockingQueue<ConnectionWrapper> connections;
    private String url;
    private String email;
    private String password;

    private ConnectionPool(String url, String email, String password, int initConnectionCount) {
        this.connections = new LinkedBlockingQueue<>();
        this.url = url;
        this.email = email;
        this.password = password;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOG.fatal("Error while register driver manager");
        }
        for (int i = 0; i < initConnectionCount; i++) {
            try {
                connections.put(getConnection());
            } catch (InterruptedException e) {
                LOG.fatal("Interrupted exception");
            } catch (ConnectionPoolException e) {
                LOG.fatal("Connection pool exception");
            }
        }
    }
    public static ConnectionPool getInstance() {
        if (!isCreated.get()&&instance==null) {
            lock.lock();
            try {
                if (!isCreated.get()) {
                    String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
                    String email = ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER);
                    String password = ConfigurationManager.getInstance().getProperty(ConfigurationManager.PASSWORD);
                    int count = Integer.parseInt(ConfigurationManager.getInstance().getProperty(ConfigurationManager.POOL_SIZE));
                    instance = new ConnectionPool(url, email, password, count);
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionWrapper getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, email, password);
        }
        catch (SQLException e) {
            throw new ConnectionPoolException("Error! Creating connection has been failed!" , e);
        }
        return new ConnectionWrapper(connection);
    }

    public ConnectionWrapper takeConnection() throws ConnectionPoolException {
        ConnectionWrapper connection;
        try {
            connection = connections.take();
        }
        catch (InterruptedException e) {
            throw new ConnectionPoolException("Error! Can't take connection!", e);
        }
        return connection;
    }

    public void putConnection(ConnectionWrapper connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            LOG.error("Interrupted Exception!");
        }
    }

    public int size() {
        return connections.size();
    }

    public void destroyConnections() {
        int counter = 0;
        while (!connections.isEmpty()){
            try {
                connections.take().destroy();
            } catch (SQLException e) {
                LOG.error("SQLException");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.info("Closed " + counter + " connections");
    }
}