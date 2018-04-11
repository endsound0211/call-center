package com.endsound.callCenter;

import com.endsound.callCenter.bean.Call;
import com.endsound.callCenter.bean.Fresher;
import com.endsound.callCenter.bean.ProductManager;
import com.endsound.callCenter.bean.TechnicalLead;

import java.util.stream.IntStream;

public class Main {
    private static CallCenterService callCenterService = CallCenterService.getInstance();

    public static void main(String[] args){
        //init call center
        callCenterService.setPm(new ProductManager(0));
        callCenterService.setTl(new TechnicalLead(1));
        IntStream.range(0, 10).forEach(i -> callCenterService.addFresher(new Fresher( i + 2)));
        //service start
        new Thread(callCenterService).start();

        //init call
        IntStream.range(0, 100).forEach(i -> callCenterService.addCall(new Call(i)));
    }

}
