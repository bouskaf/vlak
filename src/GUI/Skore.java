/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import vlak.Databaze;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 *
 * @author Raketak
 */
public class Skore {

    public static void vytvorGUI() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame skore = new JFrame("S K Ó R E");
        GridLayout g1 = new GridLayout(1, 2);
        skore.setLayout(g1);
        skore.setResizable(false);
        skore.setSize(200, 400);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        skore.setLocation((d.width - skore.getSize().width) / 2, (d.height - skore.getSize().height) / 2);
        skore.setVisible(true);
        JTextArea prezdivkaArea = Databaze.vypisDatabazi()[0];
        JTextArea skoreArea = Databaze.vypisDatabazi()[1];
        skoreArea.setBackground(Color.YELLOW);
        prezdivkaArea.setBackground(Color.YELLOW);
        skore.add(prezdivkaArea);
        skore.add(skoreArea);
    }
}
