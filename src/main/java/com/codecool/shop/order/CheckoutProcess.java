package com.codecool.shop.order;

import com.codecool.shop.controller.AbstractProcess;
import jdk.nashorn.internal.ir.RuntimeNode;

/**
 * Created by cave on 2016.11.10..
 */
public class CheckoutProcess extends AbstractProcess {
    @Override
    protected void action(Orderable order) {
        order.checkout();
    }
}
