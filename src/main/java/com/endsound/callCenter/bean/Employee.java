package com.endsound.callCenter.bean;

import com.endsound.callCenter.CallCenterService;

public abstract class Employee implements Runnable{
    private Integer id;
    private CallCenterService callCenterService;
    private EmployeeStatus status;
    private Integer level;  //the max level of call can be handled by this employee
    private Call call;      //the call is processing


    public Employee(Integer level, Integer id){
         this.callCenterService = CallCenterService.getInstance();
         this.status = EmployeeStatus.Available;
         this.level = level;
         this.id = id;
    }

    abstract public Boolean solve();


    @Override
    public void run() {
        System.out.println(String.format("[Employee:%d] is working on [Call:%d]", id, call.getId()));
        //random time to solve it
        try {
            Thread.sleep((int) (Math.random() * 3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //solve result
        Boolean result = solve();
        System.out.println(String.format("[Call:%d] is finished by [Employee:%d], the result is %s", call.getId(), id, result? "successful": "failed"));

        if(result)
            successfulHandler(call);
        else
            failedHandler(call);

        finishHandler(call);
    }

    /**
     *  fire when finish, change EmployeeStatus
     * @param call
     */
    protected void finishHandler(Call call){
        status = EmployeeStatus.Available;
    }

    /**
     * fire when  successful
     * @param call
     */
    protected void successfulHandler(Call call){

    }

    /**
     * fire when failed, escalate the call
     * @param call
     */
    protected void failedHandler(Call call){
        System.out.println(String.format("[Call:%d] is escalated", call.getId()));
        call.setLevel(call.getLevel() + 1);
        callCenterService.addCall(call);
    }

    public Employee setStatus(EmployeeStatus status) {
        this.status = status;
        return this;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public Boolean canHandle(Call call){
        return level >= call.getLevel();
    }

    public Employee setCall(Call call) {
        this.call = call;
        return this;
    }

    protected CallCenterService getCallCenterService() {
        return callCenterService;
    }
}
