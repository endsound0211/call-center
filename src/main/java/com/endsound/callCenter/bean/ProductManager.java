package com.endsound.callCenter.bean;

public class ProductManager extends Employee {
    public ProductManager(Integer id){
        super(2, id);
    }

    @Override
    public Boolean solve() {
        return true; //100% solve problem
    }
}
