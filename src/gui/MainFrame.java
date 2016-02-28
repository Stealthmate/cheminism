package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.KeyEventDispatcher;

import javax.swing.JFrame;

import gui.mainscreen.GlobalPanel;
import gui.mainscreen.SelectObserver;
import gui.mainscreen.reactionpanel.ReactantLabel;

public class MainFrame extends JFrame {

	private static int WIDTH;
	private static int HEIGHT;

	private static GlobalPanel pnlGlobal;

	public static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 15);
	public static final Color HIGHLIGHT_COLOR = new Color(0x2000AFFF, true);
	public static final Dimension DEFAULT_SIZE = new Dimension(1024, 600);

	public static int getFrameWidth() {
		return WIDTH;
	}

	public static int getFrameHeight() {
		return HEIGHT;
	}

	public static int getScreenWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().width;
	}

	public static int getScreenHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	public void resizeLayout(Dimension d) {
		pnlGlobal.resizeLayout(d);
	}

	public MainFrame(int width, int height, String name) {
		super(name);


		WIDTH = width;
		HEIGHT = height;

		setSize(new Dimension(width, height));
		pnlGlobal = new GlobalPanel(width, height);
		this.add(pnlGlobal);
		this.invalidate();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				resizeLayout(e.getComponent().getSize());
			}

		});

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if(e.isControlDown()) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_1 : SelectObserver.selectReactant(0); break;
					case KeyEvent.VK_2 : SelectObserver.selectReactant(1); break;
					case KeyEvent.VK_3 : SelectObserver.selectReactant(2); break;
					}
				}
				return false;
			}
		});
	}

}
