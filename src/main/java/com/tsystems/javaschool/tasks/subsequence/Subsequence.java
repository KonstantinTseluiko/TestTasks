package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */


    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x != null && y != null){
            if(x.size() == 0){
                return true;
            }
            Iterator iteratorY = y.iterator();
            Iterator iteratorX = x.iterator();
            Object nextX = iteratorX.next();
            while(iteratorY.hasNext()) {
                Object next = iteratorY.next();
                if (nextX.equals(next)) {
                    if(iteratorX.hasNext()){
                        nextX = iteratorX.next();
                    }
                    else {
                        return true;
                    }
                }
                else{
                    iteratorY.remove();
                }
            }
            return false;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

}
