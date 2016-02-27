package gui.mainscreen.searchbar;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import gui.MainFrame;

public class SearchPanel extends JPanel {

	private static final int TEXT_SEEK_QUERY_HEIGHT = 35;
	
	private JTextField txtSearch;
	private SuggestionList pnlSuggestions;
	
	private class SearchListener implements DocumentListener {
		
		private void update(DocumentEvent e) {
			try {
				pnlSuggestions.generateSuggestions(
						e.getDocument().getText(0, e.getDocument().getLength()));
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

	public SearchPanel(int width, int height) {

		SearchManager.registerSearchPanel(this);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		txtSearch = new JTextField();
		
		txtSearch.setMaximumSize(new Dimension(10000, TEXT_SEEK_QUERY_HEIGHT));
		txtSearch.setPreferredSize(new Dimension(width/5, TEXT_SEEK_QUERY_HEIGHT));
		txtSearch.setFont(MainFrame.MAIN_FONT);
		SUGGESTIONS_LISTENER = new SearchListener();
		
		txtSearch.getDocument().addDocumentListener(SUGGESTIONS_LISTENER);
		
		txtSearch.addKeyListener(new KeyAdapter() {
	
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ENTER : SearchManager.executeQuery(txtSearch.getText()); break;
				case KeyEvent.VK_DOWN : updateText(pnlSuggestions.highlightNext()); break;
				case KeyEvent.VK_UP : updateText(pnlSuggestions.highlightPrev()); break;
				}
			}
			
		});
		
		pnlSuggestions = new SuggestionList();
		
		this.add(txtSearch);
		this.add(pnlSuggestions);
		
	}
	
	
	/*package-private*/ void updateText(String txt) {
		if(txt != null) {
			txtSearch.getDocument().removeDocumentListener(SUGGESTIONS_LISTENER);
			txtSearch.setText(txt);
			txtSearch.getDocument().addDocumentListener(SUGGESTIONS_LISTENER);
		}
	}
	
	
	/*package-private*/ String highlightNext() {
		return pnlSuggestions.highlightNext();
	}
	
	/*package-private*/ String highlightPrev() {
		return pnlSuggestions.highlightPrev();
	}
	
	/*package-private*/ void highlight(SuggestionEntry se) {
		if(SearchManager.getHighlighted() != null)
			SearchManager.getHighlighted().unhighlight();

		SearchManager.highlight(se);
		
		if(SearchManager.getHighlighted() != null) {
			
			SearchManager.getHighlighted().highlight();

			updateText(SearchManager.getHighlighted().getSubstance().getFormula());
		}
	}
	
	/*package-private*/ void selectHighlighted() {
		pnlSuggestions.selectHighlighted();
	}
}
