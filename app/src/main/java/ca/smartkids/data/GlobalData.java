package ca.smartkids.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.smartkids.model.User;

public class GlobalData {
    private static GlobalData global_instance = null;
    private GlobalData(){}
    public static GlobalData getInstance() {
        if (global_instance == null)
            global_instance = new GlobalData();
        return global_instance;
    }

    private User user;
    private List<User> kids = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getKids() {
        return kids;
    }

    public void setKids(List<User> kids) {
        this.kids = kids;
    }
}
