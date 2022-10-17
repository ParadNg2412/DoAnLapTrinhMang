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

/**
 *
 * @author admin
 */
public class Rook extends Piece {

    static String className = "rook";

    public Rook(Point nowPosition, Image image, boolean isBelongToFirstPlayer, Chessboard chessboard) {
        super(nowPosition, image, isBelongToFirstPlayer, chessboard);
    }

    @Override
    public void generatePossibleDestination() {
        this.possibleDestinations.clear();
        int x = this.getNowPosition().x;
        int y = this.getNowPosition().y;
        //all possible destinations in the up
        for (int i = y - 1; i > -1; i--) {
            Point destination = new Point(x, i);
            Piece piece = this.chessboard.getPieceAt(destination);
            if (piece == null || (piece.isBelongToFirstPlayer() != this.isBelongToFirstPlayer())) {
                possibleDestinations.add(destination);
            }
            if (piece != null) {
                break;
            }
        }
        //all possible destinations in the down
        for (int i = y + 1; i < 8; i++) {
            Point destination = new Point(x, i);
            Piece piece = this.chessboard.getPieceAt(destination);
            if (piece == null || (piece.isBelongToFirstPlayer() != this.isBelongToFirstPlayer())) {
                possibleDestinations.add(destination);
            }
            if (piece != null) {
                break;
            }
        }
        //all possible destinations to the right
        for (int i = x + 1; i < 8; i++) {
            Point destination = new Point(i, y);
            Piece piece = this.chessboard.getPieceAt(destination);
            if (piece == null || (piece.isBelongToFirstPlayer() != this.isBelongToFirstPlayer())) {
                possibleDestinations.add(destination);
            }
            if (piece != null) {
                break;
            }
        }
        //all possible destinations to the left
        for (int i = x - 1; i > -1; i--) {
            Point destination = new Point(i, y);
            Piece piece = this.chessboard.getPieceAt(destination);
            if (piece == null || (piece.isBelongToFirstPlayer() != this.isBelongToFirstPlayer())) {
                possibleDestinations.add(destination);
            }
            if (piece != null) {
                break;
            }
        }
    }
}
