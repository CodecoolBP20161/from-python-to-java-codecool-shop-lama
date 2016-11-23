package com.codecool.shop.util;

import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntIdGenerator {
    private List<Integer> listOfIds;

    public IntIdGenerator() {
        super();
    }

    public IntIdGenerator(List<Integer> listOfIds) {
        this.listOfIds = listOfIds;
    }

    public int generateID() {
        int id = 1;
        if (listOfIds.size() == 0) return id;
        id = Collections.max(listOfIds) + 1;
        if (id < 0) id = checkIdFromOne();
        return id;
    }

    private int checkIdFromOne() {
        boolean notValidID = true;
        int id = 1;

        while (notValidID) {
            if (listOfIds.contains(id)) id++;
            else notValidID = false;
        }
        return id;
    }
}
