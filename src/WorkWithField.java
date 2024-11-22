import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class WorkWithField{
    private char[][] field;
    private int n;
    private int m;
    private int bombCount;

    private static final int BOMBCOUNT = 99;
    private static final int N = 24;
    private static final int M = 20;

    public WorkWithField(){
        n = N;
        m = M;
        field = new char[n][m];
        bombCount = BOMBCOUNT;
    }

    public WorkWithField(int n, int m, int bombCount){
        this.n = n;
        this.m = m;
        field = new char[n][m];
        this.bombCount = bombCount;
    }

    public char[][] getField() {
        return field;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void Controller(int x, int y){
        do {
            FillSpaces();
            GenerateBombs();
            ReplaceSpaces();
        } while (field[x][y] != ' ');
    }

    void FillSpaces(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                field[i][j] = ' ';
            }
        }
    }

    public void GenerateBombs(){
        while (true) {
            for (int i = 0; i < bombCount; ++i) {
                Random r = new Random();
                int x = r.nextInt(n);
                int y = r.nextInt(m);
                while (field[x][y] != ' ') {
                    x = r.nextInt(n);
                    y = r.nextInt(m);
                }
                field[x][y] = '*';
            }
            if (checkField(new Pair(0, 0))){
                break;
            }
            else{
                FillSpaces();
            }
        }
    }

    private boolean checkField(Pair start) {
        Queue<Pair> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][m];
        queue.offer(start);
        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            int x = p.x;
            int y = p.y;
            if (!visited[x][y]) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (x + i >= 0 && y + j >= 0 && x + i < n && y + j < m && !visited[x + i][y + j] && field[x + i][y + j] == ' ') {
                            queue.offer(new Pair(x + i, y + j));

                        }
                    }
                }
                visited[x][y] = true;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (field[i][j] == ' ' && !visited[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void ReplaceSpaces(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (field[i][j] == ' ') {
                    field[i][j] = ConvertSpaceToDigit(new Pair(i, j));
                }
            }
        }
    }

    private char ConvertSpaceToDigit( Pair pos) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (pos.x + i >= 0 && pos.y + j >= 0 && pos.x + i < n && pos.y + j < m){
                    if (field[pos.x + i][pos.y + j] == '*'){
                        count++;
                    }
                }
            }
        }
        if (count == 0){
            return ' ';
        }
        return (char)(count + 48);
    }
}