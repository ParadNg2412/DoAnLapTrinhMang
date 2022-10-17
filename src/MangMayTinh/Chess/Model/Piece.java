/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MangMayTinh.Chess.Model;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author admin
 */
public abstract class Piece extends JLabel {
    private boolean isAlive = true;
    private Point nowPosition;
    static String className = "piece";
    private boolean isBelongToFirstPlayer;
    ArrayList<Point> possibleDestinations = new ArrayList<>();
    Chessboard chessboard;
    
    public Piece(Point nowPosition, Image image, boolean isBelongToFirstPlayer, Chessboard chessboard) {
        super(new ImageIcon(image));
        this.nowPosition = nowPosition;
        this.isBelongToFirstPlayer = isBelongToFirstPlayer;
        this.chessboard = chessboard;
    }
    
    public abstract void generatePossibleDestination();
    
    public boolean isMoveAccepted(Move move) {
        this.generatePossibleDestination();
        for (Point destination : this.possibleDestinations) {
            if (destination.equals(move.destination)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBelongToFirstPlayer() {
        return isBelongToFirstPlayer;
    }

    public void setIsBelongToFirstPlayer(boolean isBelongToFirstPlayer) {
        this.isBelongToFirstPlayer = isBelongToFirstPlayer;
    }
    
    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
    public Point getNowPosition() {
        return nowPosition;
    }

    public void setNowPosition(Point nowPosition) {
        this.nowPosition = nowPosition;
    }
}
