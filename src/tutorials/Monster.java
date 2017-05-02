package tutorials;
import java.util.Arrays;


/**
 * Created by 1 on 23/04/2017.
 */
public class Monster {

    public int xpos = 0;
    public int ypos = 0;
    public  String name = "Monster";

    public int attack = 0;
    public int health = 100;
    public int movement = 0;
    public boolean alive = true;

    static char[][] battleBoard = new char[10][10];

    public static void buildBattleBoard(){
        for(char[] row : battleBoard){
            Arrays.fill(row,'*');
        }
    }

    public static void redrawBoard(){

        for(int i=0; i < battleBoard.length; i++){
            for(int j=0; j < battleBoard[i].length; j++){
               System.out.print("|" + battleBoard[i][j] + "|");
            }
            System.out.println();
        }
        int k =1;
        while(k <= 30){System.out.print('-'); k++;}
        System.out.println();
    }

    public int getAttack(){
        return attack;
    }

    public int getMovement(){
        return movement;
    }

    public int getHealth(){
        return health;
    }

    public boolean getAlive(){
        return alive;
    }

    public void setAttack(int attack){
        this.attack = attack;
    }

    public void setMovement(int movement){
        this.movement = movement;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public Monster(int health, int attack, int movement, String name){
    }


    }

