package planeApp.views;

import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		MainView frame = new MainView();
		frame.setVisible(true);
	}
}
