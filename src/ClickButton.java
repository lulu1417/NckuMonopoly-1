import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import ingame.Game;

public class ClickButton extends JButton {
	//ctor
	public ClickButton(int x, int y, int w, int h, String text, String signal) {
		super(text);
		this.signal = signal;
		this.setLocation(x,y);
		this.setSize(w,h);
		//listener
		ClickButton self = this;
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game.signals.add(self.getSignal());
			}
		};
		this.addActionListener(listener);
		//font
		Font font = new Font("Microsoft Jhenghei", Font.BOLD, (int) (h*0.5));
		this.setFont(font);
	}
	public ClickButton(int x, int y, int w, int h, String text) {
		this(x, y, w, h, text, text);
	}
	//get-set
	protected String getSignal() {
		return this.signal;
	}
	//var
	private String signal;
}
