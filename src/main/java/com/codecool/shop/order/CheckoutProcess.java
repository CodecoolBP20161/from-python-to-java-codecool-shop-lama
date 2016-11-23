package com.codecool.shop.order;

import com.codecool.shop.controller.AbstractProcess;
import jdk.nashorn.internal.ir.RuntimeNode;

public class CheckoutProcess extends AbstractProcess {
    @Override
    public void action(Orderable order) {
        order.checkout();
    }
}
