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
            Iterator iterator = y.iterator();
            while(iterator.hasNext()) {
                Object next = iterator.next();
                if (!x.contains(next)) {
                    iterator.remove();
                }
            }
            List<String> masks = setMask(x, y);
            if (x.size() == y.size()){
                return x.equals(y);
            }
            else if(x.size() > y.size()){
                return false;
            }else{
                return checkSub(masks);
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }


    public List<String> setMask(List alf, List sub){
        List<String> maskArray = new ArrayList<>();
        for (int pos = 0; pos < alf.size(); pos++){
            StringBuilder mask = new StringBuilder();
            int index = 0;
            try {
                List subList = sub.subList(pos, alf.size()+pos);
                for (Object elem : alf){
                    if (elem.equals(subList.get(index))){
                        mask.append("T");
                    }
                    else{
                        mask.append("F");
                    }
                    index++;
                }
                maskArray.add(mask.toString());
            }
            catch (IndexOutOfBoundsException ex){
                return maskArray;
            }

        }
        return maskArray;
    }

    public boolean checkSub(List<String> maskArr){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maskArr.get(0).length(); i++){
            sb.append("T");
        }
        String ref = sb.toString();

        for (int index = 0; index < maskArr.size(); index++){
            StringBuilder check = new StringBuilder();
            if (index != maskArr.size() - 1){
                String next = maskArr.get(index+1);
                String curr = maskArr.get(index);
                for (int pos = 0; pos < next.length(); pos++){
                    if (curr.charAt(pos) == 'T' || next.charAt(pos) == 'T' && !(curr.charAt(pos) != 'F'
                            && next.charAt(pos) != 'F')){
                        check.append("T");
                    }
                    else{
                        check.append("F");
                    }
                }
                if (ref.equals(check.toString())){
                    return true;
                }
            }
        }
        return false;
    }

}
