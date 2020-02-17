package com.jindreamit.practice.current.thread.print;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintMethods {

    private Integer flag;

    public PrintMethods() {
        this.flag=1;
    }

    public void printA(){
        log.info("A");
    }

    public void printB(){
        log.info("B");
    }

    public void printC(){
        log.info("C");
    }

    public void addFlag(){
        ++flag;
    }

    public Integer getFlag(){
        return flag;
    }

    public void resetFlag(){
        flag=1;
    }
}
