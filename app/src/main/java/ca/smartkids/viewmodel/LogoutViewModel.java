package ca.smartkids.viewmodel;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.data.GlobalData;

public class LogoutViewModel {

    public boolean logOut() {
//        GlobalData.getInstance().setUser(null);
//        GlobalData.getInstance().setKids(null);
        DataStoreManager.getInstance().saveStringData("token", "");
        DataStoreManager.getInstance().saveStringData("parent_id", "");
        return true;
    }
}
