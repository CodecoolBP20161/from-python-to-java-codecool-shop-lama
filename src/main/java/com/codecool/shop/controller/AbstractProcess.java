package com.codecool.shop.controller;

import com.codecool.shop.order.Orderable;

/**
 * Created by cave on 2016.11.10..
 */
public abstract class AbstractProcess {
    public void process(Orderable order) {
        action(order);
    }

    protected abstract void action(Orderable order);
}
