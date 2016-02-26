package gui.mainscreen.searchbar;

import java.util.EventObject;

public class SelectEvent extends EventObject {

	public SelectEvent(SuggestionEntry source) {
		super(source);
	}
}
