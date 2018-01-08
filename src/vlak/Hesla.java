/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vlak;

/**
 *
 * @author Raketak
 */
public class Hesla {

    public static String seznamHesel[] = new String[50];

    public Hesla() {
        seznamHesel[0] = "MYDLO";
        seznamHesel[1] = "LEDEN";
        seznamHesel[2] = "STROP";
        seznamHesel[3] = "LILIE";
        seznamHesel[4] = "RYBKA";
        seznamHesel[5] = "KLAUN";
        seznamHesel[6] = "BRAUN";
    }

    public static int hledejHeslo(String heslo) {
        Hesla hesla = new Hesla();
        int temp = -1;
        for (int i = 0; i < 6; i++) {
            if (Hesla.seznamHesel[i].equals(heslo)) {
                temp = i;
            }
        }
        return temp;
    }

    public static String hledejHeslo(int lvl) {
        Hesla hesla = new Hesla();
        return Hesla.seznamHesel[lvl];

    }
}
