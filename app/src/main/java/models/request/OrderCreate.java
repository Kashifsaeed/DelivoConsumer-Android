package models.request;

/**
 * Author: Uzair Qureshi
 * Date:  2/15/17.
 * Description:
 */

public class OrderCreate {
    private DeliveryItem delivery_request;

    public DeliveryItem getDelivery_request()
    {
        return delivery_request;
    }

    public void setDelivery_request(DeliveryItem delivery_request) {
        this.delivery_request = delivery_request;
    }


}
