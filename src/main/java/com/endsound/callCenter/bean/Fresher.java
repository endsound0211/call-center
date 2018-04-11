package com.endsound.callCenter.bean;

import java.util.Random;

public class Fresher extends Employee {

    public Fresher(Integer id){
        super(0, id);
    }

    @Override
    public Boolean solve() {
        return new Random().nextInt(6) <= 3; //80% solve problem
    }

    /**
     * after finishing, add fresher to CallCenterService's freshers queue
     * @param call
     */
    @Override
    protected void finishHandler(Call call){
        super.finishHandler(call);
        getCallCenterService().addFresher(this);
    }

}
