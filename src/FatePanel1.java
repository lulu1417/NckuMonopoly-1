import java.awt.Dimension;

public class FatePanel1 extends FatePanel{
	//ctor
	public FatePanel1(Dimension dim) {
		super(dim, 1);
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
