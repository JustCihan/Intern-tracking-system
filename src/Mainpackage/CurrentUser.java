package Mainpackage;

import Objects.User;

public class CurrentUser {
    private static CurrentUser instance;
    private User user;
 
    private CurrentUser() {}

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
