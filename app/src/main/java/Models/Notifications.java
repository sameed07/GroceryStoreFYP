package Models;

public class Notifications  {

    private String notificationId;
    private String notificationText;
    private String notificationTitle;
    private String getNotificationTimeStamp;
    private String getNotificationIsSeen;

    public Notifications() {
    }

    public Notifications(String notificationId, String notificationText, String notificationTitle,
                         String getNotificationTimeStamp, String getNotificationIsSeen) {
        this.notificationId = notificationId;
        this.notificationText = notificationText;
        this.notificationTitle = notificationTitle;
        this.getNotificationTimeStamp = getNotificationTimeStamp;
        this.getNotificationIsSeen = getNotificationIsSeen;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getGetNotificationTimeStamp() {
        return getNotificationTimeStamp;
    }

    public void setGetNotificationTimeStamp(String getNotificationTimeStamp) {
        this.getNotificationTimeStamp = getNotificationTimeStamp;
    }

    public String getGetNotificationIsSeen() {
        return getNotificationIsSeen;
    }

    public void setGetNotificationIsSeen(String getNotificationIsSeen) {
        this.getNotificationIsSeen = getNotificationIsSeen;
    }
}
