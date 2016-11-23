package com.codecool.shop.controller;

import com.codecool.shop.order.Orderable;

public abstract class AbstractProcess {
    public void process(Orderable order) {
        action(order);
    }

    protected abstract void action(Orderable order);
}
