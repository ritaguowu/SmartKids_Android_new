package ca.smartkids.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    public static final String API_BASE_URL = "http://192.168.31.235:5000/";

    public static Retrofit getInstance(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://199.241.133.185:5000/")
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
