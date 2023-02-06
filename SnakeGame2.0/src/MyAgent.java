import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import za.ac.wits.snake.Apple;
import za.ac.wits.snake.DevelopmentAgent;
import za.ac.wits.snake.Grid;
import za.ac.wits.snake.Snake;
import za.ac.wits.snake.utils.Point;

public class MyAgent extends DevelopmentAgent {

    public static void main(String args[]) {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" "); //Used to get the size of the grid
            int nSnakes = Integer.parseInt(temp[0]);  // Number of snakes
            int gridx = Integer.parseInt(temp[1]); //Grid X length
            int gridy = Integer.parseInt(temp[2]); //Grid Y length
            int gamemode = Integer.parseInt(temp[3]); //GameMode
            List<Point> ZombieHeads = new ArrayList<>();
            List<Point> SnakeHeads = new ArrayList<>();
           /* for(int i = 0; i < gridx ; i++) { // Intialize empty space to 0
                for(int j = 0 ; j <gridy;j++) {
                    Grid1[i][j]= -1;
                }
            }*/
            while (true) {   // may need to reset the class everytime
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }

                int[][] Grid1 = new int[gridx][gridy]; //Get the Grid Size (Represents the blocked grid
                for (int i = 0; i < gridx; i++) { // Intialize empty space to -1
                    for (int j = 0; j < gridy; j++) {
                        Grid1[i][j] = -1;
                    }
                }
                String apple1 = line; // apple position, (Extract the co ordinate using split function
                String[] xy = (line.split(" "));
                int applex = Integer.parseInt(xy[0]);
                int appley = Integer.parseInt(xy[1]); // Extracted the Co-ordinates
                //do stuff with apples
                // Grid1[applex][appley]= 12;
                for (int zombie = 0; zombie < 6; zombie++) {
                    String zombieLine = br.readLine();  // Using Draw Snake function
                    String SHead[] = zombieLine.split(" ");
                    String ZHead [] = SHead[0].split(",");
                    Point Zombiehead = new Point(Integer.parseInt(ZHead[0]),Integer.parseInt(ZHead[1])) ;
                    ZombieHeads.add(Zombiehead);
                    if (Integer.parseInt(ZHead[0])+1 <50 ) {
                        Grid1[Integer.parseInt(ZHead[0]) + 1][Integer.parseInt(ZHead[1])] = 1;
                    }
                    if (Integer.parseInt(ZHead[0])-1 >-1) {
                        Grid1[Integer.parseInt(ZHead[0])-1][Integer.parseInt(ZHead[1])]=1;
                    }
                    if (Integer.parseInt(ZHead[1])-1 >-1){
                        Grid1[Integer.parseInt(ZHead[0])][Integer.parseInt(ZHead[1])-1]=1;
                    }
                    if (Integer.parseInt(ZHead[1])+1 <50 ) {
                        Grid1[Integer.parseInt(ZHead[0])][Integer.parseInt(ZHead[1]) + 1] = 1;
                    }
                    drawSnake(zombieLine, (zombie + 100) * 10, Grid1); // Adds Zombie Snakes to the Grid
                }
                int Snakeheadx = 0;
                int Snakeheady = 0;
                int SnakeLength = 0;
                int mySnakeNum = Integer.parseInt(br.readLine()); // snake number
                for (int i = 0; i < nSnakes; i++) {  // draw out all the snakes  (put Draw Snake and DrawLine)
                    //DrawSnake,DrawLine
                    String snakeLine = br.readLine(); // Nested Method in Draw Snake
                    String[] SnakeLineVariable = snakeLine.split(" ");  //Use split function to separate the values to get the snake co-ordinates, alive
                    String Alive = SnakeLineVariable[0];
                    if (Alive.equals("alive")) {
                        Integer Length = SnakeLineVariable.length;
                        SnakeLength = Integer.parseInt(SnakeLineVariable[1]);
                        Integer Kills = Integer.parseInt(SnakeLineVariable[2]);
                        String NumSnakeLine = "";
                        for (int j = 0; j < Length - 3; j++) {
                            NumSnakeLine = NumSnakeLine + SnakeLineVariable[3 + j] + " "; // Used to Get string of variables
                        }
                        String SHead[] = SnakeLineVariable[3].split(",");
                        Point EnemySnakehead = new Point(Integer.parseInt(SHead[0]),Integer.parseInt(SHead[1])) ;
                        SnakeHeads.add(EnemySnakehead);
                        // Integer EnemySnakeheadx = Integer.parseInt(SHead[0]);
                        //Integer EnemySnakeheady = Integer.parseInt(SHead[1]);


                        drawSnake(NumSnakeLine, (i + 3) * 100, Grid1);
                        // if not dead don't necessarily have to draw it.position based on death
                        if (i == mySnakeNum) { // extract snakes head to get position. works on states. update position based on state.
                            //hey! That's me :) My Snake is so useless
                            Snakeheadx = Integer.parseInt(SHead[0]);
                            Snakeheady = Integer.parseInt(SHead[1]);
                            drawSnake(NumSnakeLine, mySnakeNum, Grid1);
                        }


                    } else {
                    }
                    //do stuff with other snakes
                }
                int snakexdiff = (Snakeheadx- applex);
                int snakeydiff = (Snakeheady- appley);
                if (SnakeLength >= 25 ){
                    for (int i = 0; i < SnakeHeads.size(); i++) {
                        if (i != mySnakeNum) {
                            String Line1 = SnakeHeads.get(i).toString();
                            String[] Split = Line1.split(",");
                            int Enemyheadx = Integer.parseInt(Split[0]);
                            int Enemyheady = Integer.parseInt(Split[1]);
                            if ((Math.abs(Enemyheady - appley) == 2) && (Math.abs(Enemyheadx - applex) == 0)) {
                                if (applex + 1 < 50) {
                                    Grid1[applex + 1][appley] = 1;
                                }
                                if (applex - (1) > -1) {
                                    Grid1[applex - (1)][appley] = 1;
                                }
                                if (appley - (1) > -1) {
                                    Grid1[applex][appley - (1)] = 1;
                                }
                                if (appley + 1  < 50) {
                                    Grid1[applex][appley + 1 ] = 1;
                                }

                            } else if ((Math.abs(Enemyheadx - applex) == 2) && (Math.abs(Enemyheady - appley) == 0)) {
                                if (applex + 1 < 50) {
                                    Grid1[applex + 1][appley] = 1;
                                }
                                if (applex - (1) > -1) {
                                    Grid1[applex - (1)][appley] = 1;
                                }
                                if (appley - (1) > -1) {
                                    Grid1[applex][appley - (1)] = 1;
                                }
                                if (appley + 1  < 50) {
                                    Grid1[applex][appley + 1 ] = 1;
                                }

                            }
                        }
                    }

                }
                int loopcount = 1;
                if (SnakeLength > 70){
                    loopcount = 2;
                }
                for (int j = 0 ; j < loopcount;j ++) {
                    for (int i = 0; i < SnakeHeads.size(); i++) {
                        if (i != mySnakeNum) {
                            String Line1 = SnakeHeads.get(i).toString();
                            String[] Split = Line1.split(",");
                            int Enemyheadx = Integer.parseInt(Split[0]);
                            int Enemyheady = Integer.parseInt(Split[1]);
                            //  if(Math.abs(Enemyheadx - applex) <3 &&(Enemyheady - appley ==0)){
                            if (Enemyheadx + 1 +j< 50) {
                                Grid1[Enemyheadx + 1+j][Enemyheady] = 1;
                            }
                            if (Enemyheadx - (1+j) > -1) {
                                Grid1[Enemyheadx - (1+j)][Enemyheady] = 1;
                            }
                            if (Enemyheady - (1+j) > -1) {
                                Grid1[Enemyheadx][Enemyheady - (1+j)] = 1;
                            }
                            if (Enemyheady + 1 +j < 50) {
                                Grid1[Enemyheadx][Enemyheady + 1 + j] = 1;
                            }
                        }
                    }
                }

                Node initialNode = new Node(Snakeheadx, Snakeheady);
                Node finalNode = new Node(applex, appley);
                AStarv2 aStar = new AStarv2(gridx, gridy, initialNode, finalNode);
                aStar.setBlocks(Grid1);
                int[][] Possible_Moves = {{1,0},{-1,0},{0,-1},{0,1}};
                List<Node> path = aStar.findPath();
                int move = 0;
                Boolean Bfound = false;
                int path12 = path.size();
                if (path.size() != 0 ) {
                    move = getMove(Grid1, Snakeheadx, Snakeheady, path, move);
                }


                else if ((path.size() == 0)) { // if path to apple is found just nearest free spot starting from downwards
                    if((isValid(Snakeheadx+1,Snakeheady,Grid1))){
                        finalNode = new Node(Snakeheadx+1, Snakeheady);
                        AStarv2 aStar0 = new AStarv2(gridx, gridy, initialNode, finalNode);
                        aStar0.setBlocks(Grid1);
                        List<Node> path0 = aStar0.findPath();
                        move = getMove(Grid1, Snakeheadx, Snakeheady, path0, move);

                    }
                    else if ((isValid(Snakeheadx-1,Snakeheady,Grid1)) ){
                        finalNode = new Node(Snakeheadx-1, Snakeheady);
                        AStarv2 aStar1 = new AStarv2(gridx, gridy, initialNode, finalNode);
                        aStar1.setBlocks(Grid1);

                        List<Node> path1 = aStar1.findPath();
                        move = getMove(Grid1, Snakeheadx, Snakeheady, path1, move);
                    }
                    else if ((isValid(Snakeheadx,Snakeheady+1,Grid1)) ) {
                        finalNode = new Node(Snakeheadx, Snakeheady+1);
                        AStarv2 aStar2 = new AStarv2(gridx, gridy, initialNode, finalNode);
                        aStar2.setBlocks(Grid1);
                        List<Node> path2 = aStar2.findPath();
                        move = getMove(Grid1, Snakeheadx, Snakeheady, path2, move);
                    }
                    else if ((isValid(Snakeheadx,Snakeheady-1,Grid1)) ) {
                        finalNode = new Node(Snakeheadx, Snakeheady-1);
                        AStarv2 aStar3 = new AStarv2(gridx, gridy, initialNode, finalNode);
                        aStar3.setBlocks(Grid1);
                        List<Node> path3 = aStar3.findPath();
                        move = getMove(Grid1, Snakeheadx, Snakeheady, path3, move);
                    }
                    else {
                        move = 5;
                    }
                }
                System.out.println(move);
                //System.out.println("log " + move);
                ZombieHeads.clear();
                SnakeHeads.clear();
                // prints the move of snake. Only thing that is anything printed out else program crashes
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int getMove(int[][] grid1, int snakeheadx, int snakeheady, List<Node> path, int move) {
        for (Node node : path) {
            int x1 = node.getRow();
            int y1 = node.getCol();
            int xdiff = snakeheadx - x1;
            int ydiff = snakeheady - y1;
            if (grid1[x1][y1] == -1) {
                if (xdiff == 0 && ydiff == -1) {
                    move = 1;
                    break;

                } else if (xdiff == 0 && ydiff == 1) {
                    move = 0;
                    break;
                } else if (xdiff == 1 && ydiff == 0) {
                    move = 2;
                    break;

                } else if (xdiff == -1 && ydiff == 0) {
                    move = 3;
                    break;
                }
            }
        }
        return move;
    }
    // Draw Snake draws the bored returns 2d array
    public static void drawSnake(String htinput , int SnakeNum, int[][] Matrix ) {
        String[] Values =  htinput.split(" ");
        int amount = Values.length;
        for(int i = 0; i < amount-1; i++) {
            if((i+1>=amount)) {
                drawLine(Matrix, Values[i-1], Values[i],SnakeNum);
            }
            else {
                drawLine(Matrix, Values[i], Values[i+1],SnakeNum);
            }
        }
    }
    //Draw Line Draws the snake Based on the corners and edges. Returns String of p
    public static void drawLine(int[][] Matrix,String start, String end, int SnakeNum ) {
        String[] v1 = start.split(",");
        String[] v2 = end.split(",");
        int x1 = Integer.parseInt(v1[0]);
        int y1 = Integer.parseInt(v1[1]);
        int x2 = Integer.parseInt(v2[0]);
        int y2 = Integer.parseInt(v2[1]);
        //Case 1 x = x && y1 > y2
        if((x1 == x2) && (y1 > y2)) {
            for(int i = y2; i <= y1; i++) {
                Matrix[x1][i] = SnakeNum;
            }
        }
        //Case 2
        if((x1 == x2) && (y2 > y1)) {
            for(int i = y1; i <= y2; i++) {
                Matrix[x1][i] = SnakeNum;
            }
        }
        //Case 3
        if((y1 == y2) && (x2 > x1)) {
            for(int i = x1; i <= x2; i++) {
                Matrix[i][y1] = SnakeNum;
            }
        }

        //Case 4
        if((y1 == y2) && (x1 > x2)) {
            for(int i = x2; i <= x1; i++) {
                Matrix[i][y1] = SnakeNum;
            }
        }
    }

    public static boolean isValid(int x, int y, int [][]Matrix)
    {
        if(x>=0 && x<=49 && y>=0 && y<=49)
        {
            if (Matrix[x][y]== -1) {
                return true;
            }
            else{
                return false;
            }

        }
        return false;
    }
}