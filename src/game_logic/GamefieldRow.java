package game_logic;

public class GamefieldRow {

	private int[] _spalten;
//	private int _spalte0;
//    private int _spalte1;
//    private int _spalte2;

    /**
     * Initialisiert eine neue SpielfeldZeile
     */
    public GamefieldRow()
    {
    	_spalten = new int[3];
    	_spalten[0] = 0;
    	_spalten[1] = 0;
    	_spalten[2] = 0;
//        _spalte0 = 0;
//        _spalte1 = 0;
//        _spalte2 = 0;
    }
       
    /**
     * Liefert den Besitzer der angegebenen Spalte in dieser Zeile.
     * 
     * @param spalte
     *            horizontale Position (0-2)
     * @return 0 (unbesetzt), 1 (Spieler 1), 2 (Spieler 2)
     */
    public int gibBesitzer(int spalte)
    {
//        int besitzer;
//        switch (spalte)
//        {
//        case 0:
//            besitzer = _spalte0;
//            break;
//
//        case 1:
//            besitzer = _spalte1;
//            break;
//
//        case 2:
//            besitzer = _spalte2;
//            break;
//
//        default:
//            throw new IllegalArgumentException(String.valueOf(spalte));
//        }
//        return besitzer;
    	return _spalten[spalte];
    }

    /**
     * Besetze die angegebene Spalte in dieser Zeile.
     * 
     * @param spalte
     *            horizontale Position (0-2)
     * @param spieler
     *            0 (leer), 1 (Spieler 1), 2 (Spieler 2)
     */
    public void besetze(int spalte, int spieler)
    {
//        switch (spalte)
//        {
//        case 0:
//            _spalte0 = spieler;
//            break;
//
//        case 1:
//            _spalte1 = spieler;
//            break;
//
//        case 2:
//            _spalte2 = spieler;
//            break;
//
//        default:
//            throw new IllegalArgumentException(String.valueOf(spalte));
//        }
    	if(spalte < 0 || spalte > 2) {
    		throw new IllegalArgumentException(String.valueOf(spalte));
    	}
    	_spalten[spalte] = spieler;
    }
    
    /**
     * Gibt an, ob die Zeile an allen Positionen belegt ist.
     */
    public boolean istVoll()
    {
        return _spalten[0] != 0 && _spalten[1] != 0 && _spalten[2] != 0;
    }
    
}
