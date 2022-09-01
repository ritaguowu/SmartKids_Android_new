package ca.smartkids.viewmodel;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.data.GlobalData;

public class LogoutViewModel {

    public boolean logOut() {
        DataStoreManager.getInstance().saveStringData("token", "");
        DataStoreManager.getInstance().saveStringData("parent_id", "");
        return true;
    }
}
