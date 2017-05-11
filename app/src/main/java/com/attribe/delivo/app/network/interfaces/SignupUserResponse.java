package com.attribe.delivo.app.network.interfaces;

import com.attribe.delivo.app.models.User;

/**
 * Created by Sabih Ahmed on 15-Jun-16.
 */
public abstract class SignupUserResponse {


   public abstract void  OnUserCreatedAndLoggedIn();
   public abstract void OnUserCreated(User data);
   public abstract void OnuserAlreadyexits();
   public abstract void OnError();
}
