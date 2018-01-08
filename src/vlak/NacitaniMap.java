package vlak;

import javax.swing.*;
import java.io.FileInputStream;

/**
 *
 * @author Raketak
 */
public class NacitaniMap {

    static int[][] pole = new int[12][20];

    public static int[][] nactiMapu(int level) throws Exception {
        String cesta;
        if (level == -1) {
            String nazev = JOptionPane.showInputDialog("Zadejte heslo levelu: ", "HESLO");
            int lvl = 0;
            if (nazev != null) {
                lvl = Hesla.hledejHeslo(nazev);
            }
            cesta = "mapy/lvl" + lvl + ".txt";
        } else {
            cesta = "mapy/lvl" + level + ".txt";
        }
        FileInputStream fis = new FileInputStream(cesta);
        int vstup;
        int i = 0;
        int j = 0;
        while ((vstup = fis.read()) >= 0) {
            if ((int) vstup != 13 && (int) vstup != 10 && (int) vstup != ' ') {
                pole[i][j] = (int) vstup - 48;
                j++;
            }
            if (j != 0 && j % 20 == 0) {
                i++;
                j = 0;
            }
            if (i > 11) {
                break;
            }
        }
        return pole;
    }
}
