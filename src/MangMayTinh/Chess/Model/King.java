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
public class King extends Piece {

    static String className = "king";

    public King(Point nowPosition, Image image, boolean isBelongToFirstPlayer, Chessboard chessboard) {
        super(nowPosition, image, isBelongToFirstPlayer, chessboard);
    }

    @Override
    public void generatePossibleDestination() {
        possibleDestinations.clear();
        int x = this.getNowPosition().x;
        int y = this.getNowPosition().y;
        int[][] offsets = { {1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1} };
        for (int[] offset : offsets) {
            Point destination = new Point(x + offset[0], y + offset[1]);
            if (this.chessboard.isInsideChessboard(destination)) {
                Piece piece = this.chessboard.getPieceAt(destination);
                if (piece == null || (piece.isBelongToFirstPlayer() != this.isBelongToFirstPlayer())) {
                    possibleDestinations.add(destination);
                }
            }
        }
    }
}
