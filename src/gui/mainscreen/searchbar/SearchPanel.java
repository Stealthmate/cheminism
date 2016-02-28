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

import gui.FontManager;
import gui.MainFrame;

public class SearchPanel extends JPanel {

	private static final int QUERY_WIDTH = 10;
	
	private static final float SEARCHBAR_PORTION = 1.0f/6.0f;
	private static final float SUGGESTIONS_PORTION = 1.0f - SEARCHBAR_PORTION;
	
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

	@Override
	public void setPreferredSize(Dimension size) {
		super.setPreferredSize(size);
		txtSearch.setPreferredSize(
				new Dimension(
						size.width, 
						(int) (size.height*SEARCHBAR_PORTION)));
		
		txtSearch.setFont(
				FontManager.calculateFont(MainFrame.MAIN_FONT, txtSearch.getPreferredSize(), QUERY_WIDTH));
		
		pnlSuggestions.setPreferredSize(
				new Dimension(
						size.width, 
						(int) (size.height*SUGGESTIONS_PORTION)));
	}
	
	public SearchPanel(int width, int height) {

		//Register with manager
		SearchManager.registerSearchPanel(this);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Init searchbox
		txtSearch = new JTextField();
		
		txtSearch.setPreferredSize(new Dimension(width, (int) (height * SEARCHBAR_PORTION)));
		txtSearch.setFont(MainFrame.MAIN_FONT);
		SUGGESTIONS_LISTENER = new SearchListener();
		
		txtSearch.getDocument().addDocumentListener(SUGGESTIONS_LISTENER);
		
		txtSearch.addKeyListener(new KeyAdapter() {
	
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				//On Enter execute a search
				case KeyEvent.VK_ENTER : {
					SearchManager.executeQuery(txtSearch.getText());
					reset();
					break;
				}
				//Up/Down cycle suggestions
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
		//If there's actually any text, make sure to disable listener before updating
		if(txt != null) {
			txtSearch.getDocument().removeDocumentListener(SUGGESTIONS_LISTENER);
			txtSearch.setText(txt);
			txtSearch.getDocument().addDocumentListener(SUGGESTIONS_LISTENER);
		}
	}
	
	
	//Self-explanatory - forwarded to SuggestionList
	/*package-private*/ String highlightNext() {
		return pnlSuggestions.highlightNext();
	}
	
	/*package-private*/ String highlightPrev() {
		return pnlSuggestions.highlightPrev();
	}
	
	/*package-private*/ void highlight(SuggestionEntry se) {
		updateText(pnlSuggestions.highlightMe(se));
	}
	
	/*package-private*/ void clearQuery() {
		updateText("");
	}
	
	private void reset() {
		SearchManager.highlight(null);
		pnlSuggestions.removeAll();
		pnlSuggestions.revalidate();
		pnlSuggestions.repaint();
	}
}
