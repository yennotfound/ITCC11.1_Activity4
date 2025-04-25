
// Tic Tac Toe is fun!

import java.awt.*;
import java.awt.event.*;

public class TicTacToeAWT extends Frame implements ActionListener {
    Button[][] buttons = new Button[3][3];
    Button btnPlayerX, btnPlayerO, btnNewGame;
    Label lblTurn;
    char currentPlayer = 'X';
    boolean gameOver = false;


    public TicTacToeAWT() {
        setTitle("Tic Tac Toe");
        setSize(500, 500);
        setLayout(new BorderLayout());
        setResizable(true);

        // Turn indicator
        lblTurn = new Label("Player X's turn", Label.CENTER);
        lblTurn.setFont(new Font("Arial", Font.BOLD, 20));
        lblTurn.setPreferredSize(new Dimension(0, 60)); 
        add(lblTurn, BorderLayout.NORTH);

        // Grid
        Panel gridPanel = new Panel(new GridLayout(3, 3));
        Font buttonFont = new Font("Arial", Font.BOLD, 36);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("");
                buttons[i][j].setFont(buttonFont);
                buttons[i][j].addActionListener(this);
                gridPanel.add(buttons[i][j]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);



        // Player indicators
        btnPlayerX = new Button("PLAYER X");
        btnPlayerO = new Button("PLAYER O");
        updatePlayerTurnUI();
        add(btnPlayerX, BorderLayout.WEST);
        add(btnPlayerO, BorderLayout.EAST);

        // New game
        btnNewGame = new Button("New Game");
        btnNewGame.setPreferredSize(new Dimension(0, 60));
        btnNewGame.addActionListener(e -> resetGame());
        add(btnNewGame, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
        EventQueue.invokeLater(() -> this.requestFocus());
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        Button btnClicked = (Button) e.getSource();
        if (!btnClicked.getLabel().equals("")) return;

        btnClicked.setLabel(String.valueOf(currentPlayer));
        if (checkWinner()) {
            gameOver = true;
            showGameOverDialog("Player " + currentPlayer + " wins!");
        } else if (isBoardFull()) {
            gameOver = true;
            showGameOverDialog("It's a tie!");
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            updatePlayerTurnUI();
        }
    }



    private void updatePlayerTurnUI() {
        lblTurn.setText("Player " + currentPlayer + "'s turn");
        btnPlayerX.setBackground(currentPlayer == 'X' ? Color.GREEN : null);
        btnPlayerO.setBackground(currentPlayer == 'O' ? Color.GREEN : null);
    }

    private boolean isBoardFull() {
        for (Button[] row : buttons)
            for (Button b : row)
                if (b.getLabel().equals(""))
                    return false;
        return true;
    }



    private boolean checkWinner() {
        // Rows and Columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getLabel().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getLabel().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getLabel().equals(String.valueOf(currentPlayer)))
                return true;

            if (buttons[0][i].getLabel().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getLabel().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getLabel().equals(String.valueOf(currentPlayer)))
                return true;
        }

        // Diagonals
        if (buttons[0][0].getLabel().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getLabel().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getLabel().equals(String.valueOf(currentPlayer)))
            return true;

        if (buttons[0][2].getLabel().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getLabel().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getLabel().equals(String.valueOf(currentPlayer)))
            return true;

        return false;
    }



    private void showGameOverDialog(String message) {
        Dialog dialog = new Dialog(this, "Game Over", true);
        dialog.setLayout(new BorderLayout());
        Label lblMessage = new Label(message, Label.CENTER);
        lblMessage.setFont(new Font("Arial", Font.BOLD, 16));
        dialog.add(lblMessage, BorderLayout.CENTER);

        Button btnOk = new Button("OK");
        btnOk.addActionListener(e -> {
            dialog.setVisible(false);
            dialog.dispose();
            resetGame();
        });
        dialog.add(btnOk, BorderLayout.SOUTH);
        dialog.setSize(250, 120);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }



    private void resetGame() {
        for (Button[] row : buttons)
            for (Button b : row)
                b.setLabel("");
        currentPlayer = 'X';
        gameOver = false;
        updatePlayerTurnUI();
        this.requestFocus();
    }


    public static void main(String[] args) {
        new TicTacToeAWT();
    }
}

