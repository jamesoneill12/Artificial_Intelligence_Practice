package MDP;

/**
 * Created by 1 on 29/04/2017.
 */

public class FormattedTablePrint {

    public static void printRow(int[] row) {
        for (int i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int twoDm[][]= new int[7][5];
        int i,j,k=1;

        for(i=0;i<7;i++) {
            for(j=0;j<5;j++) {
                twoDm[i][j]=k;
                k++;
            }
        }

        for(int[] row : twoDm) {
            printRow(row);
        }
    }
}
