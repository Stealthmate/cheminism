package gui.mainscreen.searchbar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.text.BadLocationException;

import gui.MainFrame;
import gui.mainscreen.SubstanceInfoPanel;
import gui.mainscreen.reactionpanel.ReactantLabel;
import logic.ResourceLoader;
import logic.Substance;

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 6323253787561772917L;
	
	private static final int TEXT_SEEK_QUERY_HEIGHT = 35;
	public static final int SEEK_BAR_WIDTH = 300;
	
	private JTextField txtSearch;
	private SuggestionList pnlSuggestions;
	
	private class SearchListener implements DocumentListener {
		
		private void update(DocumentEvent e) {
			try {
				pnlSuggestions.generateSuggestions(e.getDocument().getText(0, e.getDocument().getLength()));
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
	}
	
	private SearchListener SUGGESTIONS_LISTENER;
	
	
	private void updateText(String txt) {
		if(txt != null) {
			txtSearch.getDocument().removeDocumentListener(SUGGESTIONS_LISTENER);
			txtSearch.setText(txt);
			txtSearch.getDocument().addDocumentListener(SUGGESTIONS_LISTENER);
		}
	}
	
	public SearchPanel(int width, int height) {
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		txtSearch = new JTextField();
		SUGGESTIONS_LISTENER = new SearchListener();
		txtSearch.getDocument().addDocumentListener(SUGGESTIONS_LISTENER);
		
		txtSearch.addKeyListener(new KeyAdapter() {
	
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ENTER : selectSuggestion(); break;
				case KeyEvent.VK_DOWN : updateText(pnlSuggestions.highlightNext()); break;
				case KeyEvent.VK_UP : updateText(pnlSuggestions.highlightPrevious()); break;
				}
			}
			
		});
		
		txtSearch.setMaximumSize(new Dimension(10000, TEXT_SEEK_QUERY_HEIGHT));
		txtSearch.setPreferredSize(new Dimension(width/5, TEXT_SEEK_QUERY_HEIGHT));
		txtSearch.setFont(MainFrame.MAIN_FONT);
		
		
		pnlSuggestions = new SuggestionList();
		
		this.add(txtSearch);
		this.add(pnlSuggestions);
		this.add(new SubstanceInfoPanel());
		
	}
	
	/*package-private*/ void updateHighlight(String s) {
		updateText(s);
	}
	
	/*package-private*/ void selectSuggestion() {
		String result = pnlSuggestions.selectHighlighted();
		if(ReactantLabel.getSelected() != null) {
			ReactantLabel rl = ReactantLabel.getSelected();
			rl.setSubstance(Substance.getSubstanceFromName(txtSearch.getText()));
		}
	}
}
