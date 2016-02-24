package gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.BadLocationException;

import gui.MainFrame;
import gui.mainscreen.reactionpanel.ReactionPanel;
import gui.mainscreen.searchbar.SuggestionEntry;
import gui.mainscreen.searchbar.SuggestionsPanel;
import resources.ResourceLoader;
import resources.Substance;

public class GlobalPanel extends JPanel {

	private static final long serialVersionUID = -2037661004330652608L;
	
	private JPanel pnlSeekBar;
	private JTextField textSeekQuery;
	private JPanel pnlSuggestions;

	private JPanel pnlAction;
	private JPanel pnlViewCompoundBar;
	private ReactionPanel pnlEquation;

	private void updatePreferredSize(int width, int height) {
		pnlSeekBar.setPreferredSize(
				new Dimension(SuggestionsPanel.SEEK_BAR_WIDTH, height));
		pnlViewCompoundBar.setPreferredSize(new Dimension(width, height/4));
	}
	
	public void resizeLayout(Dimension d) {
		pnlEquation.resizeLayout(new Dimension(
				(int) (pnlEquation.getBounds().getWidth()), 
				(int) (pnlEquation.getBounds().getHeight())));
	}
	
	public GlobalPanel(int width, int height) {
		
		this.addComponentListener(new ComponentAdapter() {
		
			@Override
			public void componentResized(ComponentEvent evt) {
				Component c = (Component) evt.getSource();
				invalidate();
				updatePreferredSize(c.getSize().width, c.getSize().height);
				validate();
			}
			
		});
		
		this.setLayout(new BorderLayout());
		
		pnlSeekBar = new SuggestionsPanel(width, height);
		
		pnlAction = new JPanel();
		pnlAction.setLayout(new BorderLayout());
		
		pnlViewCompoundBar = new JPanel();
		pnlViewCompoundBar.setOpaque(true);
		pnlViewCompoundBar.setBackground(Color.BLUE);
		pnlViewCompoundBar.setPreferredSize(new Dimension(width, height/4));

		pnlEquation = new ReactionPanel(4*width/5, 3*height/4);
		
		pnlAction.add(pnlViewCompoundBar, BorderLayout.NORTH);
		pnlAction.add(pnlEquation, BorderLayout.CENTER);
		pnlAction.add(pnlEquation, BorderLayout.CENTER);
		this.add(pnlSeekBar, BorderLayout.WEST);
		this.add(pnlAction, BorderLayout.CENTER);
		this.invalidate();
		
		
	}
}
