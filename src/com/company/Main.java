package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        //initialize variables
        Scanner inFile = new Scanner(new File("input.txt"));

        //setting up array
        int arrGrid;
        arrGrid = Integer.parseInt(inFile.nextLine());
        System.out.format("Array size is %dx%d.\n", arrGrid, arrGrid);

        //filling array with values
        int[][] arr = new int[arrGrid][arrGrid];
        while (inFile.hasNext()) {
            for (int i = 0; i < arrGrid; i++) {
                String[] str = inFile.nextLine().split(",");
                for (int j = 0; j < arrGrid; j++ ) {
                    arr[i][j] = Integer.parseInt(str[j]);
                }
            }
        }

        //int[] optimalPath = new int[arrGrid*2];
        List<Integer> optimalPath = new ArrayList<Integer>();
        //start algorithm
        optimalPath = arrRecurs(arr, optimalPath, 0, 0);

        System.out.format("Largest value path: %d\n", optimalPath.stream().mapToInt(Integer::intValue).sum());
        System.out.format("Array: %s\n", Arrays.toString(optimalPath.toArray()));
    }

    public static List<Integer> arrRecurs(int[][] arr, List<Integer> path, int i, int j) {
        List<Integer> new_path = new ArrayList<Integer>();
        List<Integer> greater_path;
        List<Integer> temp_path;
        List<Integer> temp_path2;

        path.add(arr[i][j]);
        new_path.add(arr[i][j]);
        if (isGreaterThan(path, new_path))
            greater_path = new ArrayList<Integer>(path);
        else
            greater_path = new ArrayList<Integer>(new_path);
        //if reached the end
        if (i+1 == (arr[0].length) && j+1 == (arr[0].length)) {
            return greater_path;
        }
        //else if can only go down
        else if (i+1 < (arr[0].length) && j+1 == (arr[0].length)) {
            temp_path = arrRecurs(arr, greater_path, i+1, j);
            if (isGreaterThan(temp_path, greater_path)) {
                return temp_path;
            } else {
                return greater_path;
            }
        }
        //if can only go right
        else if (i+1 == (arr[0].length) && j+1 < (arr[0].length)) {
            temp_path = arrRecurs(arr, greater_path, i, j+1);
            if (isGreaterThan(temp_path, greater_path)) {
                return temp_path;
            } else {
                return greater_path;
            }
        }
        else {
            temp_path = arrRecurs(arr, greater_path, i+1, j);
            //if greater path is greater than temp path 1
            if (isGreaterThan(greater_path, temp_path)) {
                temp_path = new ArrayList<Integer>(greater_path);

            }
            temp_path2 = arrRecurs(arr, greater_path, i, j+1);
            if (isGreaterThan(greater_path, temp_path2)) {
                temp_path = new ArrayList<Integer>(greater_path);
            }
            if (isGreaterThan(temp_path, temp_path2)) {
                return temp_path;
            } else {
                return temp_path2;
            }
        }
    }

    public static boolean isGreaterThan(List<Integer> array_1, List<Integer> array_2) {
        if (array_1.stream().mapToInt(Integer::intValue).sum() > array_2.stream().mapToInt(Integer::intValue).sum())
            return true;
        else
            return false;
    }

    public static int debugSumOf(List<Integer> array) {
        return array.stream().mapToInt(Integer::intValue).sum();
    }
}