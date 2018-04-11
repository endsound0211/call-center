package com.endsound.callCenter;


import com.endsound.callCenter.bean.*;

import java.util.*;


public class CallCenterService implements Runnable{
    //employees
    private ProductManager pm;
    private TechnicalLead tl;
    private Queue<Fresher> freshers;
    //waiting list
    private List<Call> waitingCalls;

    private static final CallCenterService INSTANCE = new CallCenterService();

    private CallCenterService(){
        freshers = new LinkedList<Fresher>();
        waitingCalls = new ArrayList<Call>();
    }

    public static CallCenterService getInstance(){
        return INSTANCE;
    }

    /**
     * @param call the call need handler
     * @return the employee who can handle it. null if all employees are busy or nobody can handle it.
     */
    synchronized public Employee getHandler(Call call){
        if(!freshers.isEmpty() && tl.getStatus() == EmployeeStatus.OnCall && pm.getStatus() == EmployeeStatus.OnCall)
            return null;

        return Optional.ofNullable(freshers.poll())
                .filter(employee -> employee.canHandle(call))
                .map(employee -> (Employee)employee)
                .orElse(
                        Optional.ofNullable(tl)
                                .filter(employee -> employee.getStatus() == EmployeeStatus.Available && employee.canHandle(call))
                                .map(employee -> (Employee)employee)
                                .orElse(
                                        Optional.ofNullable(pm)
                                                .filter(employee -> employee.getStatus() == EmployeeStatus.Available && employee.canHandle(call))
                                                .map(employee -> (Employee)employee)
                                                .orElse(null)
                                )
                );
    }

    public CallCenterService setPm(ProductManager pm) {
        this.pm = pm;
        return this;
    }

    public CallCenterService setTl(TechnicalLead tl) {
        this.tl = tl;
        return this;
    }

    public CallCenterService addFresher(Fresher fresher){
        if(fresher.getStatus() == EmployeeStatus.Available)
            freshers.offer(fresher);
        return this;
    }

    public CallCenterService addCall(Call call){
        waitingCalls.add(call);
        return this;
    }

    /**
     *  listen waitingCalls list and find handler to solve it
     */
    @Override
    public void run() {
        while(true){

            try {
                Thread.sleep((100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!waitingCalls.isEmpty()){
                Call call = waitingCalls.remove(0);
                Employee employee = getHandler(call);

                if(Objects.nonNull(employee)){
                    employee.setCall(call);
                    employee.setStatus(EmployeeStatus.OnCall);
                    new Thread(employee).start();
                }else{
                    //back to the queue list
                    if(waitingCalls.isEmpty())
                        //to first
                        waitingCalls.add(call);
                    else
                        //to second
                        waitingCalls.add(1, call);
                }
            }
        }
    }
}
