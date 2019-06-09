//author: 邱顯倫
import java.awt.Dimension;

public class FatePanel3 extends FatePanel{
	//ctor
	public FatePanel3(Dimension dim) {
		super(dim, 3);
		//game start
		//...
		this.gameEnd(100);
	}
	//method
	@Override
	public void doTick() {
		super.doTick();
		//game playing
		//...
	}
}
