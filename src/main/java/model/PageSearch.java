package model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PageSearch {
    private StandardAnalyzer analyzer;
    private Directory index;
    private List<Page> pages;
    private List<PageSearchResult> searchResults;

    public PageSearch(Collection<Page> pages) {
        // Initialize analyzer and index, and add pages to index
    }

    public void addDoc(Page page) {
        // Logic to add a page to the index
    }

    public Collection<PageSearchResult> search(String query) {
        // Logic to perform search and return results
        return new ArrayList<>();
    }

    public StandardAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(StandardAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public Directory getIndex() {
        return index;
    }

    public void setIndex(Directory index) {
        this.index = index;
    }
}
