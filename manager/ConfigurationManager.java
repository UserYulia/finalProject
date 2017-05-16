package by.galkina.game.manager;

import java.util.ResourceBundle;

public class ConfigurationManager {
    private static ConfigurationManager instance;
    private ResourceBundle resourceBundle;
    private static final String BUNDLE_NAME = "config";
    public static final String DATABASE_URL ="db.url";
    public static final String USER ="db.user";
    public static final String PASSWORD ="db.password";
    public static final String POOL_SIZE ="db.pool.size";
    public static final String CHARSET ="db.charset";
    public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
    public static final String REGISTRATION_PAGE_PATH  = "REGISTRATION_PAGE_PATH";
    public static final String SIGN_IN_PAGE_PATH  = "SIGN_IN_PAGE_PATH";
    public static final String ERROR_PAGE_PATH  = "ERROR_PAGE_PATH";
    public static final String USER_OFFICE_PAGE_PATH="USER_OFFICE_PAGE_PATH";
    public static final String ADMIN_OFFICE_PAGE_PATH="ADMIN_OFFICE_PAGE_PATH";
    public static final String GAME_PAGE_PATH = "GAME_PAGE_PATH";
    public static final String NEWS_PAGE_PATH = "NEWS_PAGE_PATH";

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }
    public String getProperty(String key) {
        return (String)resourceBundle.getObject(key);
    }
}