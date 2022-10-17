/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MangMayTinh.Chess.Model.Interface;

import MangMayTinh.Chess.Model.Move;

/**
 *
 * @author admin
 */
public interface ChessboardInterface {
    void didMove(Move move);
    void didSendMessage(String message);
    void didClickCloseChessboard();
}
