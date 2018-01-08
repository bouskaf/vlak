package GUI;

import vlak.CelyVlak;
import vlak.Hesla;
import vlak.Mapa;
import vlak.Vagon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bouskfil
 */
public class HlavniOkno extends JFrame {

    final static int SKOK = 50;
    static Mapa map;
    static CelyVlak vlak;
    static JPanel hraciOkno = new JPanel();
    static JPanel skoreOkno = new JPanel();
    static JTextArea skore2;
    static JTextArea heslo2;
    static Timer timer;
    static int level = 0;
    static boolean konec = false;
    static int skore = 0;

    public static void vytvorGUI() throws Exception {

        // vytvoreni hlavniho okna
        JFrame hlavniOkno = new JFrame("V  L  A  K");
        hlavniOkno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hlavniOkno.setResizable(false);
        BorderLayout b1 = new BorderLayout();
        hlavniOkno.setLayout(b1);
        hlavniOkno.setBackground(Color.black);

        // vytvoreni hraciho okna
        GridLayout g1 = new GridLayout(12, 20, 0, 0);
        hraciOkno.setLayout(g1);
        hraciOkno.setBackground(Color.black);
        hraciOkno.setPreferredSize(new Dimension(1000, 600));
        hraciOkno.setFocusable(true);

        // vytvoreni hraciho okna
        skoreOkno.setBackground(Color.RED);
        skoreOkno.setPreferredSize(new Dimension(1000, 600));
        skoreOkno.setFocusable(true);
        JTextArea skoreArea = new JTextArea("pokus");
        skoreOkno.add(skoreArea);

        // posluchac stisknuti klavesy na klavesnici
        hraciOkno.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 37) {
                    vlak.setSmer(1);
                    timer.start();
                } else if (e.getKeyCode() == 38) {
                    vlak.setSmer(2);
                    timer.start();
                } else if (e.getKeyCode() == 39) {
                    vlak.setSmer(3);
                    timer.start();
                } else if (e.getKeyCode() == 40) {
                    vlak.setSmer(4);
                    timer.start();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (timer.isRunning()) {
                        timer.stop();
                        hraciOkno.grabFocus();
                    } else {
                        timer.start();
                        hraciOkno.grabFocus();
                    }
                }
            }
        });
        vytvorMapu();
        obnovMapu();
        timer();
        hlavniOkno.add(skoreOkno, BorderLayout.CENTER);
        hlavniOkno.add(hraciOkno, BorderLayout.CENTER);

        //menu
        JPanel menu = new JPanel();
        GridLayout g2 = new GridLayout(1, 5, 5, 5);
        menu.setLayout(g2);
        JButton tlacitko1 = new JButton("Nová hra");
        JButton tlacitko2 = new JButton("Stop");
        JButton tlacitko3 = new JButton("Načít mapu");
        JButton tlacitko4 = new JButton("Odejít");
        JButton tlacitko5 = new JButton("Skóre");
        Font f = new Font("monospaced", Font.BOLD, 18);
        tlacitko1.setFont(f);
        tlacitko2.setFont(f);
        tlacitko3.setFont(f);
        tlacitko4.setFont(f);
        tlacitko5.setFont(f);
        JPanel info = new JPanel();
        GridLayout g3 = new GridLayout(2, 2, 1, 1);
        info.setLayout(g3);
        JTextArea skore1 = new JTextArea(" Skóre: ");
        skore2 = new JTextArea(" " + skore);
        JTextArea heslo1 = new JTextArea(" Heslo: ");
        heslo2 = new JTextArea("MYDLO");

        skore1.setFont(f);
        skore2.setFont(f);
        heslo1.setFont(f);
        heslo2.setFont(f);
        skore2.setBackground(Color.YELLOW);
        heslo2.setBackground(Color.YELLOW);
        info.add(skore1);
        info.add(skore2);
        info.add(heslo1);
        info.add(heslo2);
        tlacitko1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                novaHra(0);
            }
        });
        tlacitko2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                    hraciOkno.grabFocus();
                }
            }
        });
        tlacitko3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                novaHra(-1);
            }
        });
        tlacitko4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                String nazev = JOptionPane.showInputDialog("Zadejte Vaši přezdívku ", "přezdívka");
                if (nazev != null) {
//                    try {
//                        Databaze.pridejZaznam(nazev, skore);
//                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
//                        Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
                System.exit(0);
            }
        });
        tlacitko5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Skore.vytvorGUI();
                } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        menu.add(tlacitko1);
        menu.add(tlacitko3);
        menu.add(info);
        menu.add(tlacitko5);
        menu.add(tlacitko4);
        menu.setPreferredSize(new Dimension(100, 50));
        menu.setBackground(Color.black);
        hlavniOkno.add(menu, BorderLayout.PAGE_END);

        //nastaveni okna
        hlavniOkno.setSize(1000, 650);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        hlavniOkno.setLocation((d.width - hlavniOkno.getSize().width) / 2, (d.height - hlavniOkno.getSize().height) / 2);
        hlavniOkno.setVisible(true);
        hraciOkno.grabFocus();
    }

    public static void dolu() throws Exception {
        int tempX = vlak.getPolohaVlakuX() + 1;
        int tempY = vlak.getPolohaVlakuY();
        if (map.validniPohyb(tempX, tempY)) {
            vlak.setPolohaVlakuX(tempX);
            vlak.setSmer(4);
            CelyVlak.zmen(tempX, tempY, 0, 4, vlak.getSeznamVagonu(), map);
            if (map.getPole()[tempX][tempY] == 2) {
                skore = skore + 20;
                skore2.setText(" " + skore);
                Vagon temp = new Vagon(tempX, tempY, 2);
                vlak.getSeznamVagonu().add(temp);
            } else {
                skore = skore - 10;
                skore2.setText(" " + skore);
            }
        } else {
            konecHry();
        }
    }

    public static void doleva() throws Exception {
        int tempX = vlak.getPolohaVlakuX();
        int tempY = vlak.getPolohaVlakuY() - 1;
        if (map.validniPohyb(tempX, tempY)) {
            vlak.setSmer(1);
            vlak.setPolohaVlakuY(tempY);
            CelyVlak.zmen(tempX, tempY, 0, 1, vlak.getSeznamVagonu(), map);
            if (map.getPole()[tempX][tempY] == 2) {
                skore = skore + 20;
                skore2.setText(" " + skore);
                Vagon temp = new Vagon(tempX, tempY, 1);
                vlak.getSeznamVagonu().add(temp);
            } else {
                skore = skore - 10;
                skore2.setText(" " + skore);
            }
        } else {
            konecHry();
        }
    }

    public static void nahoru() throws Exception {
        int tempX = vlak.getPolohaVlakuX() - 1;
        int tempY = vlak.getPolohaVlakuY();
        if (map.validniPohyb(tempX, tempY)) {
            vlak.setSmer(2);
            vlak.setPolohaVlakuX(tempX);
            CelyVlak.zmen(tempX, tempY, 0, 2, vlak.getSeznamVagonu(), map);
            if (map.getPole()[tempX][tempY] == 2) {
                skore = skore + 20;
                skore2.setText(" " + skore);
                Vagon temp = new Vagon(tempX, tempY, 2);
                vlak.getSeznamVagonu().add(temp);
            } else {
                skore = skore - 10;
                skore2.setText(" " + skore);
            }
        } else {
            konecHry();
        }
    }

    public static void doprava() throws Exception {
        int tempX = vlak.getPolohaVlakuX();
        int tempY = vlak.getPolohaVlakuY() + 1;
        if (map.validniPohyb(tempX, tempY)) {
            vlak.setSmer(3);
            vlak.setPolohaVlakuY(tempY);
            CelyVlak.zmen(tempX, tempY, 0, 3, vlak.getSeznamVagonu(), map);
            if (map.getPole()[tempX][tempY] == 2) {
                skore = skore + 20;
                skore2.setText(" " + skore);
                Vagon temp = new Vagon(tempX, tempY, 1);
                vlak.getSeznamVagonu().add(temp);
            } else {
                skore = skore - 10;
                skore2.setText(" " + skore);
            }
        } else {
            konecHry();
        }
    }

    public static void konecHry() throws Exception {
        JLabel jlabel = (JLabel) hraciOkno.getComponent(prepocetPole(vlak.getPolohaVlakuX(), vlak.getPolohaVlakuY()));
        if (vlak.getSmer() == 1 || vlak.getSmer() == 3) {
            jlabel.setIcon(Obrazky.vlakZnicenyHor);
        } else {
            jlabel.setIcon(Obrazky.vlakZnicenyVer);
        }
        konec = true;
        novaHra(level);
    }

    public static void novaHra(int lvl) {
        timer.stop();
        int temp = JOptionPane.showConfirmDialog(hraciOkno, "Chcete hrát novou hru?", "Nová hra", JOptionPane.YES_NO_OPTION);
        if (temp == JOptionPane.YES_OPTION) {
            vymazMapu();
            timer.stop();
            try {
                map = new Mapa(lvl);
            } catch (Exception ex) {
                Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
            }
            vlak = new CelyVlak(map.getStartVlakuX(), map.getStartVlakuY());
            obnovVlak();
            try {
                obnovMapu();
            } catch (Exception ex) {
                Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (konec) {
                String nazev = JOptionPane.showInputDialog("Zadejte Vaši přezdívku ", "přezdívka");
                if (nazev != null) {
//                    try {
//                        Databaze.pridejZaznam(nazev, skore);
//                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
//                        Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
                System.exit(0);
            } else {
                timer.start();
            }
        }
        konec = false;
        heslo2.setText(Hesla.hledejHeslo(level));
        hraciOkno.grabFocus();
    }

    public static void vytvorMapu() throws Exception {
        map = new Mapa(level);
        vlak = new CelyVlak(map.getStartVlakuX(), map.getStartVlakuY());
        int velikostMapy = map.getVelikostX() * map.getVelikostY();
        for (int i = 0; i < velikostMapy; i++) {
            JLabel temp = new JLabel(Obrazky.pozadi);
            hraciOkno.add(temp);
        }
        vymazMapu();
    }

    public static void vymazMapu() {
        int velikostMapy = map.getVelikostX() * map.getVelikostY();
        for (int i = 0; i < velikostMapy; i++) {
            JLabel temp = (JLabel) hraciOkno.getComponent(i);
            temp.setIcon(Obrazky.pozadi);
        }
    }

    public static void obnovMapu() throws Exception {
        for (int i = 0; i < map.getVelikostX(); i++) {
            for (int j = 0; j < map.getVelikostY(); j++) {
                // nastaveni ikon cereneho pozadi
                if (map.getPole()[i][j] == 0) {
                    JLabel temp = (JLabel) hraciOkno.getComponent(prepocetPole(i, j));
                    temp.setIcon(Obrazky.pozadi);
                }
                // nastaveni ikon zdi
                if (map.getPole()[i][j] == 1) {
                    JLabel temp = (JLabel) hraciOkno.getComponent(prepocetPole(i, j));
                    temp.setIcon(Obrazky.zed);
                }
                // nastaveni ikon diamantu
                if (map.getPole()[i][j] == 2) {
                    JLabel temp = (JLabel) hraciOkno.getComponent(prepocetPole(i, j));
                    temp.setIcon(Obrazky.diamant);
                }
                // nastaveni ikony cile
                if (map.getPole()[i][j] == 4) {
                    JLabel temp = (JLabel) hraciOkno.getComponent(prepocetPole(i, j));
                    temp.setIcon(Obrazky.cil);
                }
                // zkontroluje mapu a poku na ni uz nejsou zadne diamanty tak zmeni ikonu cile na otevreny cil
                if (map.zkontrolujMapu()) {
                    JLabel temp = (JLabel) hraciOkno.getComponent(prepocetPole(map.getPolohaCileX(), map.getPolohaCileY()));
                    temp.setIcon(Obrazky.cilOtevreny);
                    map.getPole()[map.getPolohaCileX()][map.getPolohaCileY()] = 7;
                    if (vlak.getPolohaVlakuX() == map.getPolohaCileX() && vlak.getPolohaVlakuY() == map.getPolohaCileY()) {
                        level++;
                        vymazMapu();
                        timer.stop();
                        map = new Mapa(level);
                        vlak = new CelyVlak(map.getStartVlakuX(), map.getStartVlakuY());
                        heslo2.setText(Hesla.hledejHeslo(level));
                        obnovVlak();
                        obnovMapu();
                        break;
                    }
                }
            }
        }
        obnovVlak();
        zapisDoPole();
    }

    public static void obnovVlak() {
        // vagony
        for (int k = 1; k < vlak.getSeznamVagonu().size(); k++) {
            int x = vlak.getSeznamVagonu().get(k).polohaX;
            int y = vlak.getSeznamVagonu().get(k).polohaY;
            int smerVagonu = vlak.getSeznamVagonu().get(k).smer;
            JLabel tempVagonu = (JLabel) hraciOkno.getComponent(prepocetPole(x, y));
            if (smerVagonu == 1 || smerVagonu == 3) {
                tempVagonu.setIcon(Obrazky.vagonVer);
            } else {
                tempVagonu.setIcon(Obrazky.vagonHor);
            }
        }

        // lokomotiva
        JLabel tempLokomotiva = (JLabel) hraciOkno.getComponent(prepocetPole(vlak.getSeznamVagonu().get(0).polohaX, vlak.getSeznamVagonu().get(0).polohaY));
        int smerLokomotivy = vlak.getSeznamVagonu().get(0).smer;
        if (smerLokomotivy == 1) {
            tempLokomotiva.setIcon(Obrazky.vlakDoleva);
        } else if (smerLokomotivy == 2) {
            tempLokomotiva.setIcon(Obrazky.vlakNahoru);
        } else if (smerLokomotivy == 3) {
            tempLokomotiva.setIcon(Obrazky.vlakDoprava);
        } else if (smerLokomotivy == 4) {
            tempLokomotiva.setIcon(Obrazky.vlakDolu);
        }
    }

    public static void zapisDoPole() {
        for (int k = 1; k < vlak.getSeznamVagonu().size(); k++) {
            int x = vlak.getSeznamVagonu().get(k).polohaX;
            int y = vlak.getSeznamVagonu().get(k).polohaY;
            map.getPole()[x][y] = 5;
        }
    }

    public static int prepocetPole(int i, int j) {
        return i * map.getVelikostY() + j;
    }

    public static String ziskejHeslo(int lvl) {
        return Hesla.seznamHesel[lvl];
    }

    public static void timer() {
        timer = new Timer(450, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                map.aktualizujMapu(vlak);
                if (vlak.getSmer() == 1) {
                    try {
                        doleva();
                    } catch (Exception ex) {
                        Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (vlak.getSmer() == 2) {
                    try {
                        nahoru();
                    } catch (Exception ex) {
                        Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (vlak.getSmer() == 3) {
                    try {
                        doprava();
                    } catch (Exception ex) {
                        Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (vlak.getSmer() == 4) {
                    try {
                        dolu();
                    } catch (Exception ex) {
                        Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    obnovMapu();
                } catch (Exception ex) {
                    Logger.getLogger(HlavniOkno.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
