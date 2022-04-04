package shared;

import java.io.Serializable;


/**
 * @author francoise.perrin
 *
 * Les coordonnées des cases dans le model varient
 * de [a..h] [8..1]
 */
public class ModelCoord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char col;
	private int ligne;
	

	public ModelCoord(char col, int ligne) {
		super();
		this.col = col;
		this.ligne = ligne;
	}

	public ModelCoord(ModelCoord coord) {
		super();
		this.col = coord.getCol();
		this.ligne = coord.getLigne();
	}

	@Override
	public String toString() {
		return "[col=" + col + ", ligne=" + ligne + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + ligne;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelCoord other = (ModelCoord) obj;
		if (col != other.col)
			return false;
		if (ligne != other.ligne)
			return false;
		return true;
	}

	public char getCol() {
		return col;
	}

	public int getLigne() {
		return ligne;
	}

	
}
