package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.util.IntIdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierDaoMem implements SupplierDao {

    private static List<Supplier> DATA = new ArrayList<>();
    private static SupplierDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoMem() {
    }

    public static SupplierDaoMem getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        List<Integer> idList = DATA.stream().map(BaseModel::getId).collect(Collectors.toList());
        IntIdGenerator idGenerator = new IntIdGenerator(idList);
        supplier.setId(idGenerator.generateID());
        DATA.add(supplier);
    }

    @Override
    public Supplier find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<Supplier> getAll() {
        return DATA;
    }
}
