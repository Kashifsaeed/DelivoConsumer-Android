package com.attribe.delivo.app.models.request;

import java.util.List;

/**
 * Author: Uzair Qureshi
 * Date:  5/11/17.
 * Description:
 */

public class OrderCreate {
    private Order order;
    private List<Task> tasks;
    private static OrderCreate mInstance;


    private OrderCreate() {

    }

    public static OrderCreate getInstance() {
        if (mInstance == null) {
            mInstance = new OrderCreate();
        }
        return mInstance;

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


}
