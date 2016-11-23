package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.util.IntIdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCategoryDaoMem implements ProductCategoryDao {

    private static List<ProductCategory> DATA = new ArrayList<>();
    private static ProductCategoryDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMem() {
    }

    public static ProductCategoryDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMem();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        List<Integer> idList = DATA.stream().map(BaseModel::getId).collect(Collectors.toList());
        IntIdGenerator idGenerator = new IntIdGenerator(idList);
        category.setId(idGenerator.generateID());
        DATA.add(category);
    }

    @Override
    public ProductCategory find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        return DATA;
    }
}
