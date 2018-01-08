package vlak;

/**
 *
 * @author Raketak
 */
public class Mapa {

    private int velikostX;
    private int velikostY;
    private int startVlakuX;
    private int startVlakuY;
    private int polohaCileX;
    private int polohaCileY;
    private int[][] pole;

    public Mapa(int level) throws Exception {

        // 0 - volne misto
        // 1 - zed
        // 2 - potrava
        // 3 - vlak
        // 4 - cil
        // 5 - vagon
        // 6 - prazdne misto po diamantu
        // 7 - otevreny cil

        this.velikostX = 12;
        this.velikostY = 20;

        this.pole = NacitaniMap.nactiMapu(level);
        for (int i = 0; i < velikostX; i++) {
            for (int j = 0; j < velikostY; j++) {
                if (pole[i][j] == 3) {
                    this.startVlakuX = i;
                    this.startVlakuY = j;
                }
                if (pole[i][j] == 4) {
                    this.polohaCileX = i;
                    this.polohaCileY = j;
                }
            }
        }
    }

    public void aktualizujMapu(CelyVlak vlak) {
        pole[vlak.getPolohaVlakuX()][vlak.getPolohaVlakuY()] = 3;
    }

    public boolean jeNaMape(int x, int y) {
        if (x > velikostX || y > velikostY || x < 0 || y < 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validniPohyb(int x, int y) {
        if (jeNaMape(x, y)) {
            if (pole[x][y] != 1 && pole[x][y] != 4 && pole[x][y] != 5 && pole[x][y] != 3) {
                return true;
            }
        }
        return false;
    }

    public void tiskniMapu() {
        for (int i = 0; i < velikostX; i++) {
            System.out.println("");
            for (int j = 0; j < velikostY; j++) {
                System.out.printf(pole[i][j] + " ");
            }
        }
    }

    public boolean zkontrolujMapu() {
        int pocetPotravy = 0;
        for (int i = 0; i < velikostX; i++) {
            for (int j = 0; j < velikostY; j++) {
                if (pole[i][j] == 2) {
                    pocetPotravy++;
                }
            }
        }
        if (pocetPotravy > 0) {
            return false;
        } else {
            return true;
        }
    }

    public int[][] getPole() {
        return pole;
    }

    public void setPole(int[][] pole) {
        this.pole = pole;
    }

    public int getStartVlakuX() {
        return startVlakuX;
    }

    public void setStartVlakuX(int startVlakuX) {
        this.startVlakuX = startVlakuX;
    }

    public int getStartVlakuY() {
        return startVlakuY;
    }

    public void setStartVlakuY(int startVlakuY) {
        this.startVlakuY = startVlakuY;
    }

    public int getVelikostX() {
        return velikostX;
    }

    public void setVelikostX(int velikostX) {
        this.velikostX = velikostX;
    }

    public int getVelikostY() {
        return velikostY;
    }

    public void setVelikostY(int velikostY) {
        this.velikostY = velikostY;
    }

    public int getPolohaCileX() {
        return polohaCileX;
    }

    public void setPolohaCileX(int polohaCileX) {
        this.polohaCileX = polohaCileX;
    }

    public int getPolohaCileY() {
        return polohaCileY;
    }

    public void setPolohaCileY(int polohaCileY) {
        this.polohaCileY = polohaCileY;
    }
}
