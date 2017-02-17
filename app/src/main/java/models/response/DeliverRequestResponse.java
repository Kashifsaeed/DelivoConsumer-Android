package models.response;

/**
 * Author: Uzair Qureshi
 * Date:  2/15/17.
 * Description:
 */

public class DeliverRequestResponse
{
    private Data data;
    private MetaInfo meta;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public MetaInfo getMeta() {
        return meta;
    }

    public void setMeta(MetaInfo meta) {
        this.meta = meta;
    }



    public class Data
    {
        private DeliveryRequest delivery_request;

        public DeliveryRequest getDelivery_request() {
            return delivery_request;
        }

        public void setDelivery_request(DeliveryRequest delivery_request) {
            this.delivery_request = delivery_request;
        }


    }

}
