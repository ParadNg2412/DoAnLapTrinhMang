/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MangMayTinh.Chess.Connection.Client;

import MangMayTinh.Chess.Model.Chessboard;
import MangMayTinh.Chess.Model.Interface.ChessboardInterface;
import MangMayTinh.Chess.Model.Enum.MessageType;
import MangMayTinh.Chess.Model.Move;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author thinhle
 */
public class Client extends javax.swing.JFrame implements ChessboardInterface {

    Socket socket = null;
    int port = 0;
    String host = "";
    ObjectOutputStream sender = null;
    ObjectInputStream receiver = null;
    String myName = "";
    String secondPlayerName = "";
    Thread listener;
    Chessboard chessboard;
    boolean isMyTurn = false;
    boolean isFirstPlayer = false;
    boolean isRunning = false;

    /**
     * Creates new form Client
     */
    public Client() {
        initComponents();
        this.getRootPane().setDefaultButton(btnJoin);
    }

    // --------------------- private function ---------------------------
    private void play() {
        this.isRunning = true;
        this.listener = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        MessageType type = (MessageType) receiver.readObject();
                        System.out.println("type: " + type);
                        switch (type) {
                            case string:
                                String messageFromServer = (String) receiver.readObject();
                                lbMessage.setText(messageFromServer);
                                break;
                            case move:
                                Move move = (Move) receiver.readObject();
                                Move newMove = move.clone();
                                moveOpponent(newMove);
                                break;
                            case result:
                                boolean isWinner = (boolean) receiver.readObject();
                                informResult(isWinner);
                                break;
                            case startGame:
                                startGame();
                                break;
                            case turn:
                                isMyTurn = true;
                                break;
                            case isFirstPlayer:
                                boolean isFirst = (boolean) receiver.readObject();
                                isFirstPlayer = isFirst;
                                sendMessageToServer(MessageType.name, myName);
                                break;
                            case name:
                                secondPlayerName = (String) receiver.readObject();
                                break;
                            case message:
                                String message = (String) receiver.readObject();
                                chessboard.addMessageHistory(message, !isFirstPlayer);
                                break;
                            default:
                                System.out.println("Can not cast data from socket to expect message type!");
                                break;
                        }
                    } catch (IOException ex) {
                        System.out.println("IO Exception: From client (play): " + ex.getMessage());
                        System.out.println("caused by: " + ex.getCause().toString());
                    } catch (ClassNotFoundException ex) {
                        System.out.println("Class Not Found Exception: From client (play)");
                        ex.printStackTrace();
                    }
                }
            }
        });
        this.listener.start();
    }

    private void moveOpponent(Move move) {
        System.out.println("Opponent's move: ");
        this.chessboard.move(move);
        this.isMyTurn = true;
        this.chessboard.setMessage("Your turn!");
        if (isFirstPlayer) {
            this.chessboard.switchTurn(1);
        } else {
            this.chessboard.switchTurn(2);
        }
    }

    private void informResult(boolean isWinner) {
        String title = this.myName;
        String message = "YOU WIN!";
        if (!isWinner) {
            message = "YOU LOSE!";
        }
        this.lbMessage.setText(message);
        this.chessboard.setMessage(message);
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        this.chessboard.destruct();
        this.chessboard.dispose();
        this.btnJoin.setEnabled(true);
        this.isRunning = false;
        this.sendOperation(MessageType.endGame);
    }

    private void startGame() {
        this.chessboard = new Chessboard();
        this.chessboard.setIsFirstPlayer(isFirstPlayer);
        this.chessboard.drawChessboard();
        this.chessboard.setDelegate(this);
        this.chessboard.setVisible(true);
        if (this.isMyTurn) {
            chessboard.setMessage("It's your turn!");
        } else {
            this.chessboard.setMessage("Your opponent's turn!");
        }
        if (this.isFirstPlayer) {
            this.chessboard.setPlayerName(this.myName, secondPlayerName);
        } else {
            this.chessboard.setPlayerName(secondPlayerName, this.myName);
        }
    }

    private void didChangeInput() {
        if (!this.txtServerIP.getText().equals("") && !this.txtPort.getText().equals("") && !this.txtName.getText().equals("")) {
            this.btnJoin.setEnabled(true);
        } else {
            this.btnJoin.setEnabled(false);
        }
    }

    private <T> void sendMessageToServer(MessageType type, T data) {
        try {
            sender.writeObject(type);
            sender.writeObject(data);
        } catch (Exception e) {
            System.out.print("Send data Error: Client");
            System.out.println(e.toString());
        }
    }

    public void sendOperation(MessageType type) {
        try {
            this.sender.writeObject(type);
        } catch (Exception e) {
            System.out.print("Send data Error: ");
            System.out.println(e.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtServerIP = new javax.swing.JTextField();
        txtPort = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        btnJoin = new javax.swing.JButton();
        lbMessage = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jMenuItem1.setText("jMenuItem1");

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chess client");
        setBackground(new java.awt.Color(153, 153, 153));
        setResizable(false);

        jLabel1.setText("Server IP:");

        jLabel2.setText("Port:");

        jLabel3.setText("Name:");

        txtServerIP.setText("localhost");
        txtServerIP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtServerIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                didChangeServerIP(evt);
            }
        });

        txtPort.setText("5555");
        txtPort.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                didChangePort(evt);
            }
        });

        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nameDidChange(evt);
            }
        });

        btnJoin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnJoin.setText("New Game");
        btnJoin.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 204, 0), new java.awt.Color(51, 102, 0)));
        btnJoin.setEnabled(false);
        btnJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinActionPerformed(evt);
            }
        });

        lbMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 241, Short.MAX_VALUE)
                .addComponent(lbMessage)
                .addContainerGap(246, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addComponent(txtServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(btnJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbMessage)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(btnJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void didChangeServerIP(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_didChangeServerIP
        this.didChangeInput();
    }//GEN-LAST:event_didChangeServerIP

    private void didChangePort(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_didChangePort
        this.didChangeInput();
    }//GEN-LAST:event_didChangePort

    private void nameDidChange(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameDidChange
        this.didChangeInput();
    }//GEN-LAST:event_nameDidChange

    private void btnJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinActionPerformed
        String portString = this.txtPort.getText();
        String host = this.txtServerIP.getText();
        String name = this.txtName.getText();
        try {
            int port = Integer.parseInt(portString);
            this.socket = new Socket(host, port);
            this.lbMessage.setForeground(Color.blue);
            this.lbMessage.setText("Connected to server!");
            this.sender = new ObjectOutputStream(this.socket.getOutputStream());
            this.receiver = new ObjectInputStream(this.socket.getInputStream());
            //this.sendMessageToServer(MessageType.name, name);
            this.host = host;
            this.port = port;
            this.myName = name;
            this.btnJoin.setEnabled(false);
            this.play();
        } catch (Exception e) {
            System.out.println(e);
            this.lbMessage.setText("Failed to connect to server: " + host + " with port: " + portString);
        }
    }//GEN-LAST:event_btnJoinActionPerformed

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getPlayerName() {
        return myName;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnJoin;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel lbMessage;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtServerIP;
    // End of variables declaration//GEN-END:variables

    @Override
    public void didMove(Move move) {
        //System.out.println("Move from client: " + move.getSource().x + " " + move.getSource().y + " to : " + move.getDestination().x + " " + move.getDestination().y);
        if (!isMyTurn) {
            this.chessboard.setMessage("It's not your turn!");
            return;
        }
        //System.out.println("My move: ");
        this.chessboard.setMessage("Your opponent's turn!");
        this.sendMessageToServer(MessageType.move, move);
        this.chessboard.move(move);
        this.isMyTurn = false;
        if (isFirstPlayer) {
            this.chessboard.switchTurn(2);
        } else {
            this.chessboard.switchTurn(1);
        }
    }

    @Override
    public void didSendMessage(String message) {
        this.sendMessageToServer(MessageType.message, message);
    }

    @Override
    public void didClickCloseChessboard() {
        this.sendOperation(MessageType.surrender);
    }
}
