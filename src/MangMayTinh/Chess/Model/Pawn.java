/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MangMayTinh.Chess.Model;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author admin
 */
public class Pawn extends Piece {

    static String className = "pawn";

    public Pawn(Point nowPosition, Image image, boolean isBelongToFirstPlayer, Chessboard chessboard) {
        super(nowPosition, image, isBelongToFirstPlayer, chessboard);
    }

    @Override
    public void generatePossibleDestination() {
        this.possibleDestinations.clear();
        int x = this.getNowPosition().x;
        int y = this.getNowPosition().y;
        
        if (y == 6 && this.chessboard.getPieceAt(new Point(x, y - 1)) == null) {
            Point destination = new Point(x, y - 2);
            Piece piece = this.chessboard.getPieceAt(destination);
            if (piece == null || (piece.isBelongToFirstPlayer() != this.isBelongToFirstPlayer())) {
                possibleDestinations.add(destination);
            }
        }
        
        for (int i = -1; i < 2; i++) {
            Point destination = new Point(x + i, y - 1);
            Piece piece = this.chessboard.getPieceAt(destination);
            if ((piece != null && (piece.isBelongToFirstPlayer() != this.isBelongToFirstPlayer()) && x + i != x) || (piece == null && i == 0)) {
                possibleDestinations.add(destination);
            }
        }
    }
}
