package com.company.PlayingProcess;

import com.company.BPStuff.TicTacToeEvents.Move;
import com.company.BPStuff.TicTacToeEvents.X;
import il.ac.bgu.cs.bp.bpjs.model.BEvent;

import java.util.LinkedList;
import java.util.List;

public class Game {
    /**
     * The board that we play with
     */
    private Spot[][] board;
    /**
     * Represents the side size of the board
     */
    private static final int side = 3;
    /**
     * Keeps the moves that were played in the game
     */
    private List<Move> moves;
    /**
     * The result of this game
     */
    private GameResult result;

    public Game() {
        board = new Spot[side][side];
        moves = new LinkedList<>();
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Spot.Empty;
            }
        }
    }

    public void AddMove(Move move){
        Spot spot;
        if(move instanceof X){
            spot = Spot.X;
        }
        else{
            spot = Spot.O;
        }
        int row = move.row;
        int col = move.col;
        board[row][col] = spot;
        moves.add(move);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            builder.append("|");
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == Spot.Empty)
                    builder.append(" ");
                else
                    builder.append(board[row][col]);
                builder.append("|");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public double fitness(){
        if(result == GameResult.OWin)
            return 1;
        else if(result == GameResult.XWin)
            return 0;
        else
            return 0.3;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(BEvent event) {
        if(event instanceof Move) {
            try {
                throw new Exception("The event given must be a result event");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        switch (event.name) {
            case "Draw":
                this.result = GameResult.Draw;
                break;
            case "XWin":
                this.result = GameResult.XWin;
                break;
            case "OWin":
                this.result = GameResult.OWin;
                break;
            default:
                try {
                    throw new Exception("The event given must be a result event");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
