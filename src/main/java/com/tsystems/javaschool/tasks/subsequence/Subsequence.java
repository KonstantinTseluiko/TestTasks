package com.tsystems.javaschool.tasks.subsequence;

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

    public static class Node{
        int index;
        int count;

        public Node (int index, int count){
            this.index= index;
            this.count = count;
        }
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        HashMap<Object, Integer> mapCount = new HashMap<>();
        if (x != null && y != null){
            for (Object elem : y){
                if(mapCount.containsKey(elem)){
                    int newCount = mapCount.get(elem) + 1;
                    mapCount.put(elem,newCount);
                }
                else{
                    mapCount.put(elem,1);
                }
            }
            Iterator iterator = y.iterator();
            while(iterator.hasNext()) {
                Object next =  iterator.next();
                if (!x.contains(next)) {
                    iterator.remove();
                    if(mapCount.get(next) == 1){
                        mapCount.remove(next);
                    }
                }
            }
            int pos = 0;

            while (pos < x.size()){
                Object next =x.get(pos);
                if ( mapCount.get(next) > 1){
                    while (mapCount.get(next) > 1){
                        y.remove(next);
                    }
                }
            }

            return x.equals(y);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

}
