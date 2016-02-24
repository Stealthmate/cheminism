package gui.mainscreen.searchbar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import gui.MainFrame;
import resources.ResourceLoader;
import resources.Substance;

public class SuggestionsPanel extends JPanel {

	private static final long serialVersionUID = 6323253787561772917L;
	
	private static final int TEXT_SEEK_QUERY_HEIGHT = 35;
	public static final int SEEK_BAR_WIDTH = 300;
	
	private JPanel pnlList;
	private JTextField txtSearch;
	
	public SuggestionsPanel(int width, int height) {
		pnlList = new JPanel();
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		txtSearch = new JTextField();
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			
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
		
		txtSearch.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ENTER : selectSuggestion(); break;
				case KeyEvent.VK_DOWN : selectNextSuggestion(); break;
				case KeyEvent.VK_UP : selectPreviousSuggestion(); break;
				}
			}
			
		});
		
		txtSearch.setMaximumSize(new Dimension(10000, TEXT_SEEK_QUERY_HEIGHT));
		txtSearch.setPreferredSize(new Dimension(width/5, TEXT_SEEK_QUERY_HEIGHT));
		txtSearch.setFont(MainFrame.MAIN_FONT);
		
		pnlList = new JPanel();
		
		pnlList.setLayout(new GridBagLayout());
		pnlList.setPreferredSize(
				new Dimension(width/5, height-TEXT_SEEK_QUERY_HEIGHT));
		
		this.add(txtSearch);
		this.add(pnlList);
		
	}
	
	
	private void populateSuggestions(String query) {
		
		SuggestionEntry.setHighlighted(null);
		pnlList.removeAll();
		pnlList.revalidate();
		pnlList.repaint();
		
		if(query.length() == 0) return;
		
		ArrayList<Substance> substances = ResourceLoader.getSubstanceListMatching(query);

		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.gridy = 10;
		c.fill = GridBagConstraints.VERTICAL;
		
		JPanel dummy = new JPanel();
		dummy.setOpaque(false);
		pnlList.add(dummy, c);
		
		c.gridx = 0;
		c.gridy = -1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		
		for(Substance s : substances) {
			c.gridy++;
			SuggestionEntry entry = new SuggestionEntry(s.getName());
			pnlList.add(entry, c);
			pnlList.revalidate();
		}
	}
	
	private void selectSuggestion() {
		if(SuggestionEntry.getHighlighted() != null) {
			System.out.println("Not yet implemented");
		}
	}
	
	private void selectNextSuggestion() {
		
		int n = pnlList.getComponentCount();
		
		if(SuggestionEntry.getHighlighted() == null) {
			if(n > 0)
				SuggestionEntry.setHighlighted(
						(SuggestionEntry)pnlList.getComponent(1));
			return;
		}
	
		for(int i=1;i<=n - 1; i++) {
			if(pnlList.getComponent(i) == SuggestionEntry.getHighlighted()) {
				if(i < n - 1) {
					SuggestionEntry.setHighlighted(
							(SuggestionEntry) pnlList.getComponent(i+1));
					return;
				}
				
				else {
					SuggestionEntry.setHighlighted(
							(SuggestionEntry) pnlList.getComponent(1));
					return;
				}
			}
		}
	}
	
	private void selectPreviousSuggestion() {
		
		int n = pnlList.getComponentCount();
		
		if(SuggestionEntry.getHighlighted() == null && n > 0) {
			SuggestionEntry.setHighlighted(
					(SuggestionEntry)pnlList.getComponent(
							n-1));
			return;
		}
		
		for(int i=1;i<=n - 1; i++) {
			if(pnlList.getComponent(i) == SuggestionEntry.getHighlighted()) {
				if(i > 1) {
					SuggestionEntry.setHighlighted(
							(SuggestionEntry)pnlList.getComponent(i-1));
				return;
				}
				else if (i == 1 && pnlList.getComponentCount() > 0) {
					SuggestionEntry.setHighlighted(
							(SuggestionEntry)pnlList.getComponent(n-1));
					return;
				}
			}
		}
	}
}
