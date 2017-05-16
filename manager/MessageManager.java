package by.galkina.game.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("messages", new Locale("en", "EN"))),
    RU(ResourceBundle.getBundle("messages", new Locale("ru", "RU")));
    private static final String EN_LOCALE="en_EN";
    private static final String RU_LOCALE="ru_RU";
    private ResourceBundle resourceBundle;

    public static final String LOGIN_IS_NOT_VALID = "email.is.not.valid";
    public static final String PASSWORD_IS_NOT_VALID = "password.is.not.valid";
    public static final String EMAIL_IS_NOT_VALID = "username.is.not.valid";

    public static final String REGISTRATION_WAS_SUCCESSFUL_MESSAGE = "registration.was.successful";
    public static final String REGISTRATION_WAS_INTERRUPT_MESSAGE = "registration.was.interrupt";
    public static final String LOGIC_EXCEPTION_ERROR_MESSAGE = "logic.exception.error.message";
    public static final String LOGIN_ERROR_MESSAGE = "email.error.message";
    public static final String LOGIN_BANNED_MESSAGE = "email.banned.message";
    public static final String EDIT_EMAIL_IS_USED = "edit.email.is.used";
    public static final String SUCCESS_PAYMENT = "office.payment.success";
    public static final String ERROR_PAYMENT = "office.payment.error";
    public static final String SUBSCRIBE_IS_ALREADY_IN_USE = "subscribe.is.already.in.use";
    public static final String UNSUPPORTED_COMMAND = "command.unsupported";
    public static final String EMAIL_NOT_FOUND = "email.not.found";
    public static final String SUCCESS_CHANGE_PASSWORD = "change.password";
    public static final String SUCCESS_CHANGE_PERSONAL_DATA = "change.personal-data";
    public static final String SUCCESS_RESTORE_PASSWORD = "restore.password";
    public static final String PASSWORDS_NOT_EQUALS = "passwords.not.equals";
    public static final String INCORRECT_SUM = "incorrect.sum";
    public static final String NOT_ENOUGH_MONEY_MESSAGE = "not.enough.money.message";
    public static final String MESSAGE_WASNT_SENT = "message.wasnt.sent";

    MessageManager(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public static MessageManager getManagerByLocale(String locale) {
        MessageManager messageManager;
        if (locale != null) {
            switch (locale.trim()) {
                case EN_LOCALE:
                    messageManager =  MessageManager.EN;
                    break;
                case RU_LOCALE:
                    messageManager = MessageManager.RU;
                    break;
                default:
                    messageManager = MessageManager.RU;
            }
        } else {
            messageManager = MessageManager.RU;
        }
        return messageManager;
    }

    public String getProperty(String key) {
        return (String)resourceBundle.getObject(key);
    }
}