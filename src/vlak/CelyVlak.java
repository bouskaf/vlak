package vlak;

import java.util.ArrayList;

/**
 *
 * @author Raketak
 */
public class CelyVlak {

    private int polohaVlakuX;
    private int polohaVlakuY;
    private ArrayList<Vagon> seznamVagonu;
    private int smer; // 1 - doleva, 2 - nahoru, 3 - doprava, 4 - dolu

    public CelyVlak(int startX, int startY) {
        this.polohaVlakuX = startX;
        this.polohaVlakuY = startY;
        this.smer = 3;
        this.seznamVagonu = new ArrayList<>();
        Vagon lokomotiva = new Vagon(polohaVlakuX, polohaVlakuY, smer);
        this.seznamVagonu.add(lokomotiva);

    }

    public void pripojVagon() {
        Vagon temp = new Vagon(polohaVlakuX, polohaVlakuY, 1);
        seznamVagonu.add(temp);
    }

    public static void zmen(int x, int y, int poloha, int smer, ArrayList<Vagon> seznam, Mapa map) {
        int tempx = seznam.get(poloha).polohaX;
        int tempy = seznam.get(poloha).polohaY;
        int tempSmer = seznam.get(poloha).smer;
        seznam.get(poloha).polohaX = x;
        seznam.get(poloha).polohaY = y;
        seznam.get(poloha).smer = smer;
        poloha++;
        if (poloha < seznam.size()) {
            zmen(tempx, tempy, poloha, tempSmer, seznam, map);
        } else {
            map.getPole()[tempx][tempy] = 0;
        }
    }

    public int getPolohaVlakuX() {
        return polohaVlakuX;
    }

    public void setPolohaVlakuX(int polohaVlakuX) {
        this.polohaVlakuX = polohaVlakuX;

    }

    public int getPolohaVlakuY() {
        return polohaVlakuY;
    }

    public void setPolohaVlakuY(int polohaVlakuY) {
        this.polohaVlakuY = polohaVlakuY;
    }

    public ArrayList<Vagon> getSeznamVagonu() {
        return seznamVagonu;
    }

    public void setSeznamVagonu(ArrayList<Vagon> seznamVagonu) {
        this.seznamVagonu = seznamVagonu;
    }

    public int getSmer() {
        return smer;
    }

    public void setSmer(int smer) {
        this.smer = smer;
    }
}
