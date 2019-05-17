import java.awt.Dimension;

import javax.swing.JPanel;

public class StartPanel extends MainPanel{
	//ctor
	public StartPanel(Dimension dim) {
		super(dim);
		//start button
		{
			int w = 250, h = 100;
			ClickButton startButton = new ClickButton((this.getWidth()-w)/2, (this.getHeight()-h)/2, w, h, "Start");
			this.add(startButton);
		}
	}
	//var
}
