package com.company;
import java.io.*;
import java.util.Scanner;

public class Vankin{

    public static void main(String[] args) throws FileNotFoundException {

        //initialize variables
        Scanner inFile = new Scanner(new File("input.txt"));

        //setting up array
        int arrGrid;
        arrGrid = Integer.parseInt(inFile.nextLine());

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
        //start algorithm
        int optimalPath = greatestValueFun(arr, arrGrid);
        //console log result
        System.out.format("%d\n", optimalPath);

        //output to file
        try {
            PrintWriter outFile = new PrintWriter("output.txt", "UTF-8");
            outFile.println(optimalPath);
            outFile.close();
        } catch(IOException ie) {
        ie.printStackTrace();
    }
}


    public static int greatestValueFun(int[][] board, int arrGrid) {
        //create array to put in greatest values
        int[][] board_2 = new int[arrGrid][arrGrid];
        int down = 0; int right = 0;
        //start i at the end
        int counter = arrGrid-1;
        //algorithm
        for (int outer=arrGrid-1; outer >= 0; outer--) {
            for (int i=counter; i>=0; i--) {
                if (i == arrGrid-1 && outer == arrGrid-1) {
                    board_2[i][i] = board[i][i];
                }
                else {
                    //check columns (by going left) right
                    if (i+1 != arrGrid && board[outer][i] < board_2[outer][i+1]+board[outer][i])
                        right = board_2[outer][i+1]+board[outer][i];
                    else
                        right = board[outer][i];
                    //check columns (by going left) down
                    if (outer+1 != arrGrid && board[outer][i] < board_2[outer+1][i]+board[outer][i])
                        down = board_2[outer+1][i]+board[outer][i];
                    else
                        down = board[outer][i];
                    if (right > down)
                        board_2[outer][i] = right;
                    else
                        board_2[outer][i] = down;

                    //check rows (by going up) right
                    if (outer+1 != arrGrid && board[i][outer] < board_2[i][outer+1]+board[i][outer])
                        right = board_2[i][outer+1]+board[i][outer];
                    else
                        right = board[i][outer];
                    //check rows (by going up) down
                    if (i+1 != arrGrid && board[i][outer] < board_2[i+1][outer]+board[i][outer])
                        down = board_2[i+1][outer]+board[i][outer];
                    else
                        down = board[i][outer];
                    if (right > down)
                        board_2[i][outer] = right;
                    else
                        board_2[i][outer] = down;
                }
            }
            //keep decreasing i until the coordinate is at the top left
            counter--;
        }
        //Find the greatest value in array
        int greatestValue = board_2[0][0];
        for (int i = 0; i < arrGrid; i++) {
            for (int j = 0; j <arrGrid; j++) {
                if (board_2[i][j] > greatestValue)
                    greatestValue = board_2[i][j];
            }
        }
        return greatestValue;
    }
}