package game_logic;

public class Gamefield {

	private GamefieldRow _zeile0;
    private GamefieldRow _zeile1;
    private GamefieldRow _zeile2;

    /**
     * Initialisiert ein neues, leeres Spielfeld.
     */
    public Gamefield()
    {
        _zeile0 = new GamefieldRow();
        _zeile1 = new GamefieldRow();
        _zeile2 = new GamefieldRow();
    }

    /**
     * Gibt den Besitzer der angegebenen Position auf dem Spielfeld.
     * 
     * @param zeile
     *            vertikale Position (0-2)
     * @param spalte
     *            horizontale Position (0-2)
     * @return 0 (unbesetzt), 1 (Spieler 1), 2 (Spieler 2)
     */
    public int gibBesitzer(int zeile, int spalte)
    {
        int besitzer;
        switch (zeile)
        {
        case 0:
            besitzer = _zeile0.gibBesitzer(spalte);
            break;

        case 1:
            besitzer = _zeile1.gibBesitzer(spalte);
            break;

        case 2:
            besitzer = _zeile2.gibBesitzer(spalte);
            break;

        default:
            throw new IllegalArgumentException(String.valueOf(zeile));
        }
        return besitzer;
    }

    /**
     * Besetzt die angegebene Position auf dem Spielfeld für einen Spieler.
     * 
     * @param zeile
     *            vertikale Position (0-2)
     * @param spalte
     *            horizontale Position (0-2)
     * @param spieler
     *            0 (leer), 1 (Spieler 1), 2 (Spieler 2)
     */
    public void besetzePosition(int zeile, int spalte, int spieler)
    {
        switch (zeile)
        {
        case 0:
            _zeile0.besetze(spalte, spieler);
            break;

        case 1:
            _zeile1.besetze(spalte, spieler);
            break;

        case 2:
            _zeile2.besetze(spalte, spieler);
            break;

        default:
            throw new IllegalArgumentException(String.valueOf(zeile));
        }
    }

    /**
     * Gibt an, ob das Spielfeld an allen Positionen belegt ist.
     */
    public boolean istVoll()
    {
        return _zeile0.istVoll() && _zeile1.istVoll() && _zeile2.istVoll();
    }
}
