package csula.cs4660;

import java.util.Scanner;

/**
 * Created by luisf on 9/24/2016.
 *
 * 3
 ---
 -m-
 p--
 Sample output

 DOWN
 LEFT
 *
 */
public class Solution2 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int bX = input.nextInt();
        int bY = input.nextInt();

        char[][] tiles = new char[n][n];

        String currLine;

        for(int i = 0; i < n; i++) {

            currLine = input.next();

            for(int j = 0; j < n; j++) {

                if ((j != bX) && (i != bY)) tiles[i][j] = currLine.charAt(j);
                else  tiles[i][j] = 'm';
            }
        }
        input.close();

        displayPathtoPrincess(n,tiles);
    }

    /*
    Complete the function nextMove which takes in 4 parameters - an integer N, integers r and c
    indicating the row & column position of the bot and the character array grid - and outputs the next move
    the bot makes to rescue the princess.
     */

    public static void nextMove(int n, int r, int x, char[][] grid) {

    }


    public static void displayPathtoPrincess(int n, char[][] grid) {

        String[] moves = {"UP", "DOWN", "LEFT", "RIGHT"};

        int botX = 0;
        int botY = 0;

        int princessX = 0;
        int princessY = 0;

        for(int i = 0; i < n; i++) {

            for(int j = 0; j < n; j++) {

                if (grid[i][j] == 'm') {
                    botX = j;
                    botY = i;
                }

                if (grid[i][j] == 'p') {
                    princessX = j;
                    princessY = i;
                }
            }
        }

        while(botX != princessX) {

            int diffX = princessX - botX;

            if (diffX > 0) {
                System.out.println(moves[3]);
                botX++;
            }
            else {
                System.out.println(moves[2]);
                botX--;
            }

            while(botY != princessY) {

                int diffY = princessY - botY;

                if (diffY > 0) {
                    System.out.println(moves[1]);
                    botY++;
                }
                else {
                    System.out.println(moves[0]);
                    botY--;
                }
            }
        }
    }

}



