package ca.smartkids.repository;

import org.json.JSONObject;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.data.GlobalData;
import ca.smartkids.model.User;
import ca.smartkids.model.UserResponse;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {
    public SignUpRepository(){
    }

    public void signUpRemote(User user, SignUpRepository.ISignUpResponse signUpResponse){
        APIService retrofitService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<UserResponse> call = retrofitService.createUser(user);


        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    signUpResponse.onResponse(response.body());
                    User remoteUser = response.body().getUser();
                    DataStoreManager.getInstance().saveStringData("token", remoteUser.getAccess_token());
                    DataStoreManager.getInstance().saveStringData("parent_id", remoteUser.getUser_id());
                    GlobalData.getInstance().setUser(remoteUser);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        signUpResponse.onFailure(new Throwable(jObjError.get("error").toString()));
                    } catch (Exception e) {
                        System.out.println("System Error");
                    }

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                signUpResponse.onFailure(t);
            }
        });
    }

    public interface ISignUpResponse{
        void onResponse(UserResponse userResponse);
        void onFailure(Throwable t);
    }
}
