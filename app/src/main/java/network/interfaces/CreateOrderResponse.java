package network.interfaces;

import models.response.ResponseNewOrder;

/**
 * Created by Sabih Ahmed on 09-Jun-16.
 */
public interface CreateOrderResponse {

    void orderCreatedSuccessfully(ResponseNewOrder body);
    void tokenExpired();
    void failure(String s);

}
