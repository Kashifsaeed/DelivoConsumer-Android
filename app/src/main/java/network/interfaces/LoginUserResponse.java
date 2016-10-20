package network.interfaces;

import models.response.GenerateTokenResponse;

/**
 * Created by Sabih Ahmed on 15-Jun-16.
 */
public interface LoginUserResponse {

    void OnLoggedIn();
    void OnLoggedInFailed();
    void onLoginError();
   // void OnSuccessLogin(GenerateTokenResponse tokenResponse);
}
