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
import resources.ResourceLoader;
import resources.Substance;

public class GlobalPanel extends JPanel {

	private static final long serialVersionUID = -2037661004330652608L;
	
	private JPanel pnlSeekBar;
	private JTextField textSeekQuery;
	private static final int TEXT_SEEK_QUERY_HEIGHT = 35;
	private static final int SEEK_BAR_WIDTH = 300;
	private JPanel pnlSuggestions;

	private JPanel pnlAction;
	private JPanel pnlViewCompoundBar;
	private ReactionPanel pnlEquation;

	private void updatePreferredSize(int width, int height) {
		pnlSeekBar.setPreferredSize(
				new Dimension(SEEK_BAR_WIDTH, height));
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
		
		pnlSeekBar = new JPanel();
		pnlSeekBar.setLayout(new BoxLayout(pnlSeekBar, BoxLayout.PAGE_AXIS));
		
		textSeekQuery = new JTextField();
		textSeekQuery.getDocument().addDocumentListener(new DocumentListener() {
			
			private void update(DocumentEvent e) {
				try {
					populateSuggestions(e.getDocument().getText(0, e.getDocument().getLength()));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				update(e);
			}
		});
		
		textSeekQuery.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ENTER : selectSuggestion(); break;
				case KeyEvent.VK_DOWN : selectNextSuggestion(); break;
				case KeyEvent.VK_UP : selectPreviousSuggestion(); break;
				}
			}
			
		});
		
		textSeekQuery.setMaximumSize(new Dimension(10000, TEXT_SEEK_QUERY_HEIGHT));
		textSeekQuery.setPreferredSize(new Dimension(width/5, TEXT_SEEK_QUERY_HEIGHT));
		textSeekQuery.setFont(MainFrame.MAIN_FONT);
		
		pnlSuggestions = new JPanel();
		
		pnlSuggestions.setLayout(new GridBagLayout());
		pnlSuggestions.setPreferredSize(
				new Dimension(width/5, height-TEXT_SEEK_QUERY_HEIGHT));
		
		pnlSeekBar.add(textSeekQuery);
		pnlSeekBar.add(pnlSuggestions);
		
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
	
	private void populateSuggestions(String query) {
		
		SuggestionEntry.setHighlighted(null);
		pnlSuggestions.removeAll();
		pnlSuggestions.revalidate();
		pnlSuggestions.repaint();
		
		if(query.length() == 0) return;
		
		ArrayList<Substance> substances = ResourceLoader.getSubstanceListMatching(query);

		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.gridy = 10;
		c.fill = GridBagConstraints.VERTICAL;
		
		JPanel dummy = new JPanel();
		dummy.setOpaque(false);
		pnlSuggestions.add(dummy, c);
		
		c.gridx = 0;
		c.gridy = -1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		//c.insets = new Insets(1, 1, 1, 1);
		
		for(Substance s : substances) {
			c.gridy++;
			SuggestionEntry entry = new SuggestionEntry(s.getName());
			pnlSuggestions.add(entry, c);
			pnlSuggestions.revalidate();
		}
	}
	
	private void selectSuggestion() {
		if(SuggestionEntry.getHighlighted() != null) {
			System.out.println("To be implemented");
		}
	}
	
	private void selectNextSuggestion() {
		if(SuggestionEntry.getHighlighted() == null) {
			if(pnlSuggestions.getComponentCount() > 0)
				SuggestionEntry.setHighlighted(
						(SuggestionEntry)pnlSuggestions.getComponent(1));
			return;
		}
	
		for(int i=1;i<=pnlSuggestions.getComponentCount() - 1; i++) {
			if(pnlSuggestions.getComponent(i) == SuggestionEntry.getHighlighted()) {
				if(i < pnlSuggestions.getComponentCount() - 1) {
					SuggestionEntry.setHighlighted(
							(SuggestionEntry) pnlSuggestions.getComponent(i+1));
					return;
				}
				
				else {
					SuggestionEntry.setHighlighted(
							(SuggestionEntry) pnlSuggestions.getComponent(1));
					return;
				}
			}
		}
	}
	
	private void selectPreviousSuggestion() {
		if(SuggestionEntry.getHighlighted() == null && pnlSuggestions.getComponentCount() > 0) {
			SuggestionEntry.setHighlighted(
					(SuggestionEntry)pnlSuggestions.getComponent(
							pnlSuggestions.getComponentCount()-1));
			return;
		}
		
		for(int i=1;i<=pnlSuggestions.getComponentCount() - 1; i++) {
			if(pnlSuggestions.getComponent(i) == SuggestionEntry.getHighlighted()) {
				if(i > 1) {
					SuggestionEntry.setHighlighted(
							(SuggestionEntry)pnlSuggestions.getComponent(i-1));
				return;
				}
				else if (i == 1 && pnlSuggestions.getComponentCount() > 0) {
					SuggestionEntry.setHighlighted(
							(SuggestionEntry)pnlSuggestions.getComponent(pnlSuggestions.getComponentCount()-1));
					return;
				}
			}
		}
	}

}
