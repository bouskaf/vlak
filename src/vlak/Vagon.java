package vlak;

/**
 *
 * @author Raketak
 */
public class Vagon {

    public int polohaX;
    public int polohaY;
    public int smer; //1 - doleva, 2 - nahoru, 3 - doprava, 4 - dolu

    public Vagon(int polohaX, int polohaY, int smer) {
        this.polohaX = polohaX;
        this.polohaY = polohaY;
        this.smer = smer;
    }
}
