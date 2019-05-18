package ingame;

import java.awt.Point;

public class Cell extends Point{
	//ctor
	public Cell(int x, int y, CellType cellType) {
		super(x,y);
		this.cellType = cellType;
	}
	//get-set
	public CellType getCellType() {
		return this.cellType;
	}
	//var
	private CellType cellType;
}
