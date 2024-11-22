import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSapperGUI extends GameSapper {
    private JFrame frame;
    private JButton[][] buttons;
    private boolean isFirstClick = true;
    private WorkWithField workWithField;

    public GameSapperGUI(WorkWithField workWithField) {
        super(workWithField);
        this.workWithField = workWithField;
        buttons = new JButton[n][m];
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Sapper Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(n, m));

        for (var i = 0; i < n; i++) {
            for (var j = 0; j < m; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttons[i][j].setPreferredSize(new Dimension(50, 50));

                buttons[i][j].setOpaque(true);
                buttons[i][j].setBackground(Color.LIGHT_GRAY);
                buttons[i][j].setForeground(Color.BLACK);

                final int x = i;
                final int y = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (isFirstClick) {
                            workWithField.Controller(x, y);
                            isFirstClick = false;
                        }
                        if (buttons[x][y].isEnabled()) {
                            OpenPos(new Pair(x, y));
                            updateField();
                        }
                    }
                });

                frame.add(buttons[i][j]);
            }
        }
        frame.pack();
        frame.setVisible(true);
    }

    private void updateField() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (myField[i][j] == ' ') {
                    buttons[i][j].setBackground(Color.GRAY);
                    buttons[i][j].setEnabled(false);
                } else if (myField[i][j] == '*') {
                    buttons[i][j].setText("X");
                    buttons[i][j].setBackground(Color.RED);
                    buttons[i][j].setEnabled(false); // Мина - блокируем
                } else if (myField[i][j] == '-') {
                    if ((i + j) % 2 == 0) {
                        buttons[i][j].setBackground(new Color(10,95,56));
                    }
                    else{
                        buttons[i][j].setBackground(new Color(124,252,0));
                    }
                } else {
                    buttons[i][j].setText(String.valueOf(myField[i][j])); // Отображаем цифру
                    buttons[i][j].setBackground(Color.GRAY);
                    buttons[i][j].setEnabled(false); // Кнопка заблокирована после открытия
                }
            }
        }
    }

    private void gameOver() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (field[i][j] == '*') {
                    myField[i][j] = '*';
                    buttons[i][j].setText("X");
                    buttons[i][j].setBackground(Color.RED);
                }
                buttons[i][j].setEnabled(false);
            }
        }
    }

    @Override
    protected void OpenPos(Pair pos) {
        if (field[pos.x][pos.y] == '*') {
            gameOver();
        } else {
            super.OpenPos(pos);
            System.out.println( super.countFields() + " " + bombCount);
            if (super.countFields() == bombCount){
                JOptionPane.showMessageDialog(frame, "Вы выиграли!");
            }
        }
    }
}
