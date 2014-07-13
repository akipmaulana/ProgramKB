package aprisma.akirah.bingung.slidingmenu;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggest extends SearchRecentSuggestionsProvider {

	public static final String AUTHORITY = "aprisma.akirah.bingung.slidingmenu.SearchSuggest";

	public static final int MODE = DATABASE_MODE_QUERIES;

	public SearchSuggest() {
		setupSuggestions(AUTHORITY, MODE);
	}

}
