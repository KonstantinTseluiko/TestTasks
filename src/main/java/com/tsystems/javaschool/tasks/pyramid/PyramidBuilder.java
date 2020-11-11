package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {
        // TODO : Implement your solution here
        if(inputNumbers.contains(null)){
            throw new CannotBuildPyramidException();
        }
        Set<Integer> ts = new TreeSet<>(inputNumbers);
        if(ts.size() == 1){
            throw new CannotBuildPyramidException();
        }
        Iterator<Integer> iterator = ts.iterator();
        int n = 1;
        while ((2 * ts.size()) != 2 * n + n * (n - 1)) {
            n++;
            if(n>ts.size()){
                throw new CannotBuildPyramidException();
            }
        }
        System.out.println(n);
        int sizeRow = n;
        int sizeColumn = 2*n - 1;
        int[][] arr = new int[sizeRow][sizeColumn];


        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeColumn; j++) {
                arr[i][j] = 0;
            }
        }
        arr[0][sizeColumn / 2] = iterator.next();
        for (int i = 0; i < sizeRow - 1; i++) {
            for (int j = 0; j < sizeColumn; j++) {
                if (arr[i][j] != 0 || (j == sizeColumn/2 && i == 0)) {
                    if (arr[i + 1][j - 1] == 0) {
                        arr[i + 1][j - 1] = iterator.next();
                    }
                    if (arr[i + 1][j + 1] == 0) {
                        arr[i + 1][j + 1] = iterator.next();
                    }
                }
            }
        }

        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeColumn; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
        return arr;
    }
}
