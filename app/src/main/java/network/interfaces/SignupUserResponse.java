package network.interfaces;

/**
 * Created by Sabih Ahmed on 15-Jun-16.
 */
public abstract class SignupUserResponse {


   public abstract void  OnUserCreatedAndLoggedIn();
   public abstract void OnUserCreated();
   public abstract void OnError();
}
