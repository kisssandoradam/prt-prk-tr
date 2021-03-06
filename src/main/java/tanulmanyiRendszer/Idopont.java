package tanulmanyiRendszer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Egy olyan időpontot reprezentál, aminek
 * van egy napja, órája és perce.
 * @author adam
 *
 */
public class Idopont {
	/**
	 * Ennek a segítségével lehet naplóüzeneteket létrehozni.
	 */
	private static Logger logger = LoggerFactory.getLogger(Idopont.class);
	
	/**
	 * A hét napjait tartalmazza.
	 * @author adam
	 *
	 */
	public static enum Napok {
		/**
		 * A napok vannak felsorolva magyar nyelven.
		 */
		Hétfő, Kedd, Szerda, Csütörtök, Péntek, Szombat, Vasárnap
	}

	/**
	 * Az időpont napját tárolja.
	 */
	private Napok nap;
	
	/**
	 * Az időpont óráját tárolja.
	 */
	private int óra;
	
	/**
	 * Az időpont percét tárolja.
	 */
	private int perc;

	/**
	 * @param nap Az időpont napja.
	 * @param óra Az időpont órája.
	 * @param perc Az időpont perce.
	 * @throws TanulmanyiRendszerKivetel Ha érvénytelen az időpont.
	 */
	public Idopont(Napok nap, int óra, int perc) throws TanulmanyiRendszerKivetel {
		super();
		if (nap == null || óra >= 24 || óra < 0 || perc >= 60 || perc < 0) {
			throw new TanulmanyiRendszerKivetel("Érvénytelen időpont.");
		}
		this.nap = nap;
		this.óra = óra;
		this.perc = perc;
		logger.trace("Új {} lett példányosítva: {}.", new Object[] {getClass().getName(), this});
	}

	/**
	 * Ezt a konstruktort meghívva, az időpont perce 0 lesz.
	 * @param nap Az időpont napja.
	 * @param óra Az időpont órája.
	 * @throws TanulmanyiRendszerKivetel Ha érvénytelen az időpont.
	 */
	public Idopont(Napok nap, int óra) throws TanulmanyiRendszerKivetel {
		this(nap, óra, 0);
	}

	/**
	 * Visszaadja az időpont napját.
	 * 
	 * @return Visszaadja az időpont napját.
	 */
	public Napok getNap() {
		return nap;
	}

	/**
	 * Visszaadja az időpont óráját.
	 * 
	 * @return Visszaadja az időpont óráját.
	 */
	public int getÓra() {
		return óra;
	}

	/**
	 * Visszaadja az időpont percét.
	 * 
	 * @return Visszaadja az időpont percét.
	 */
	public int getPerc() {
		return perc;
	}

	/**
	 * Beállítja az időpont napját.
	 * 
	 * @param nap Erre a napra lesz beállítva az időpont napja.
	 */
	public void setNap(Napok nap) {
		this.nap = nap;
	}

	/**
	 * Beállítja az időpont óráját.
	 * 
	 * @param óra Erre az órára lesz beállítva az időpont órája.
	 */
	public void setÓra(int óra) {
		this.óra = óra;
	}

	/**
	 * Beállítja az időpont percét.
	 * 
	 * @param perc Erre a percre lesz beállítva az időpont perce.
	 */
	public void setPerc(int perc) {
		this.perc = perc;
	}

	/**
	 * Visszaadja az időpont sztringreprezentációját.
	 * 
	 * @return Visszaadja az időpont sztringreprezentációját.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nap).append(" ");
		sb.append(óra).append(":");
		if (perc % 100 < 10) {
			sb.append(0);
		}
		sb.append(perc);
		return sb.toString();
	}

	/**
	 * Visszaadja a nap hashCode-ját.
	 * 
	 * @param nap Ennek a napnak lesz visszaadva a hashCode-ja.
	 * @return Visszaadja a nap hashCode-ját.
	 */
	private int napHashCode(Napok nap) {
		switch (nap) {
		case Hétfő:		return 1;
		case Kedd:		return 2;
		case Szerda:	return 3;
		case Csütörtök: return 4;
		case Péntek:	return 5;
		case Szombat:	return 6;
		case Vasárnap:	return 7;
		default:		return 0;
		}
	}
	
	/**
	 * Minden időpont hashCode-ja így néz ki:
	 * {NAPOK}9{ÓRA}9{PERC}.
	 * A napok 1, az órák és a percek 2 karakterpozíciót
	 * foglalnak el, egymástól egy-egy 9-es számjeggyel
	 * vannak elválasztva, ezáltal biztosítva azt, hogy
	 * az osztály összes lehetséges példányára különböző
	 * hashCode-ot adjon vissza, ha a példányok valamely
	 * értéke különbözik egymástól.
	 * 
	 * Példa Hétfő 10:15 esetén: 1910915
	 * 
	 * @return Visszaadja az időpont hashCode-ját.
	 */
	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder(7);		
		
		// a nap hashCode-ja.
		sb.append(napHashCode(this.nap));

		// nap - óra határolószám
		sb.append("9");
		
		if (this.óra / 10 == 0) {
			sb.append(0);
		}
		sb.append(this.óra);
		
		// óra - perc határolószám
		sb.append("9");
		
		if (this.perc / 10 == 0) {
			sb.append(0);
		}
		sb.append(this.perc);
		
		return Integer.valueOf(sb.toString());
	}

	/**
	 * Két időpont akkor tekinthető egyformának, ha
	 * a napjuk, órájuk és a percük is megegyezik.
	 * 
	 * @param obj Ezzel az objektummal kerül összehasonlításra az aktuális példány.
	 * @return <code>true</code> ha a paraméterül kapott objektum megegyezik az
	 * aktuális példánnyal, <code>false</code> egyébként.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Idopont)) {
			return false;
		}
		Idopont other = (Idopont) obj;
		if (óra != other.óra) {
			return false;
		}
		if (perc != other.perc) {
			return false;
		}
		if (nap != other.nap) {
			return false;
		}
		return true;
	}

}
