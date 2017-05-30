package com.attribe.delivo.app.models.response;

import com.attribe.delivo.app.models.request.Task;

import java.util.List;

/**
 * Author: Uzair Qureshi
 * Date:  5/24/17.
 * Description:
 */

public class OrderResponse
{
    public Meta meta;
    public Data data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }





    public class Data{
        public Order order;
        public List<OrderTask> tasks = null;

    }

}
