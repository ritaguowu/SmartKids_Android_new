package ca.smartkids.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.datastore.core.DataStore;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import org.reactivestreams.Subscription;

import java.util.List;

import ca.smartkids.model.User;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DataStoreManager {

    public static final DataStoreManager instance = new DataStoreManager();
    private static final Handler dataStoreManagerHandler = new Handler(Looper.getMainLooper());

    private DataStoreManager() {}

    private DataStore<Preferences> dataStore;

    public void init(Context context){
        dataStore = new RxPreferenceDataStoreBuilder(context, /*name=*/ "users").build();
    }


    public void saveStringData(String keyName, String value){
        Preferences.Key<String> key = new Preferences.Key<>(keyName);
        RxDataStore.updateDataAsync(dataStore,
                preferences -> {
                    MutablePreferences mutablePreferences = preferences.toMutablePreferences();
                    String currentKey = preferences.get(key);

                    if (currentKey == null){
                        saveStringData(keyName, value);
                    }
                    mutablePreferences.set(key, currentKey != null? value: "");
                    return Single.just(mutablePreferences);
                }).subscribe();
    }


    public void getStringValue(String keyName, StringValueDelegate stringValueDelegate){
        Preferences.Key<String> key = new Preferences.Key<>(keyName);
        RxDataStore.data(dataStore).map(prefs -> prefs.get(key))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        dataStoreManagerHandler.post(()->stringValueDelegate.onGetValue(s));
                    }

                    @Override
                    public void onError(Throwable t) { }

                    @Override
                    public void onComplete() {  }
                });

    }


    public interface StringValueDelegate{
        void onGetValue(String s);
    }

}
