/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vlak;

import javax.swing.*;
import java.sql.*;

/**
 *
 * @author Raketak
 */
public class Databaze {

    public static void pridejZaznam(String prezdivka, int skore) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String url = "jdbc:derby://localhost:1527/VlakSkore";
        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        try (Connection conn = DriverManager.getConnection(url, "vlak", "vlak");
             Statement st = conn.createStatement()) {
            st.executeUpdate("INSERT INTO APP.TABULKA (PREZDIVKA,SKORE) VALUES ('" + prezdivka + "'," + skore + ")");
            // uzavření dotazu i všech výsledků
            st.close();
            // uzavření spojení
            conn.close();
        }
    }

    public static JTextArea[] vypisDatabazi() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String url = "jdbc:derby://localhost:1527/VlakSkore";
        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        JTextArea prezdivka = new JTextArea();
        JTextArea skore = new JTextArea();
        JTextArea pole[] = new JTextArea[2];
        pole[0] = prezdivka;
        pole[1] = skore;
        try (Connection conn = DriverManager.getConnection(url, "vlak", "vlak");
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(
                    "select * from APP.TABULKA ORDER BY SKORE DESC");
            // vypsání výsledku
            prezdivka.append("Přezdívka:\n");
            skore.append("skóre:\n");
            while (rs.next()) {
                prezdivka.append(rs.getString(1));
                prezdivka.append("\n");
                skore.append(rs.getString(2));
                skore.append("\n");
            }
            // uzavření dotazu i všech výsledků
            st.close();
            // uzavření spojení
            conn.close();
        }
        return pole;
    }
}
