package shared;

import java.io.Serializable;


/**
 * @author francoise.perrin
 *
 * Les coordonnées des cases sur le damier de la vue
 * varient de [0..7]
 */
public class GUICoord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x, y;
	
	/**
	 * @param x
	 * @param y
	 */
	public GUICoord(int x, int y) {
		this.x = x; 
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GUICoord other = (GUICoord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	
	
}
