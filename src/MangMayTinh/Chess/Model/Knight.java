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
public class Knight extends Piece {

    static String className = "knight";

    public Knight(Point nowPosition, Image image, boolean isBelongToFirstPlayer, Chessboard chessboard) {
        super(nowPosition, image, isBelongToFirstPlayer, chessboard);
    }

    @Override
    public void generatePossibleDestination() {
        possibleDestinations.clear();
        int[][] offsets = { {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1} };
        for (int[] offset : offsets) {
            Point destination = new Point(this.getNowPosition().x + offset[0], this.getNowPosition().y + offset[1]);
            if (this.chessboard.isInsideChessboard(destination)) {
                Piece piece = this.chessboard.getPieceAt(destination);
                if (piece == null || (piece.isBelongToFirstPlayer() != this.isBelongToFirstPlayer())) {
                    possibleDestinations.add(destination);
                }
            }
        }
    }
}
