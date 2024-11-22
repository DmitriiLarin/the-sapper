import java.util.LinkedList;
import java.util.Queue;

public class GameSapper {
    public int n;
    public int m;
    public int bombCount;
    public char[][] field;
    public char [][] myField;

    public GameSapper(WorkWithField workWithField){
        n = workWithField.getN();
        m = workWithField.getM();
        bombCount = workWithField.getBombCount();
        field = workWithField.getField();
        myField = new char[n][m];
        FillField();
    }

    void FillField(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                myField[i][j] = '-';
            }
        }
    }
    protected int countFields(){
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (myField[i][j] == '-'){
                    count++;
                }
            }
        }
        return count;
    }


    protected void OpenPos(Pair pos){

        Queue<Pair> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][m];

        if (field[pos.x][pos.y] == ' '){
            queue.offer(pos);
        }
        else{
            myField[pos.x][pos.y] = field[pos.x][pos.y];
        }
        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            int x = p.x;
            int y = p.y;
            if (!visited[x][y]) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (x + i >= 0 && y + j >= 0 && x + i < n && y + j < m && !visited[x + i][y + j]) {
                            if (field[x + i][y + j] == ' ') {
                                queue.offer(new Pair(x + i, y + j));
                            }
                            else if (field[x + i][y + j] != ' ' && field[x + i][y + j] != '*') {
                                visited[x + i][y + j] = true;
                            }
                        }
                    }
                }
                visited[x][y] = true;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (visited[i][j]) {
                    myField[i][j] = field[i][j];
                }
            }
        }
    }
}
