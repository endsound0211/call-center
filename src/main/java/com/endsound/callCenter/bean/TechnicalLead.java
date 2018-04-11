package com.endsound.callCenter.bean;

import java.util.Random;

public class TechnicalLead extends Employee {

    public TechnicalLead(Integer id){
        super(1, id);
    }

    @Override
    public Boolean solve() {
        return new Random().nextInt(6) <= 4; //90% solve problem
    }
}
