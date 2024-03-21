package model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * The PageSearch class is responsible for indexing and searching pages using Apache Lucene.
 * It utilizes a HashMap to store pages and provides methods to perform search operations.
 */
public class PageSearch {
    private StandardAnalyzer analyzer; // The analyzer used for indexing and searching
    private Directory index; // The Lucene index directory
    private HashMap<String, Page> pages; // HashMap to store pages
    private List<PageSearchResult> searchResults; // List to store search results

    /**
     * Constructs a new PageSearch object with the specified pages.
     * It initializes the analyzer, index, and indexes the provided pages.
     *
     * @param pages the pages to be indexed and searched
     */
    public PageSearch(HashMap<String, Page> pages) {
        this.analyzer = new StandardAnalyzer();
        this.index = new ByteBuffersDirectory();
        this.pages = pages;

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (IndexWriter writer = new IndexWriter(index, config)) {
            for (Page page : this.pages.values()) {
                addDoc(writer, page.getTitle(), page.getContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a document to the Lucene index.
     *
     * @param w       the IndexWriter to use
     * @param title   the title of the page
     * @param content the content of the page
     * @throws IOException if an I/O error occurs
     */
    public void addDoc(IndexWriter w, String title, String content) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("title", title, Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        w.addDocument(doc);
    }

    /**
     * Searches for pages matching the given query string.
     *
     * @param queryString the query string to search for
     * @return a collection of PageSearchResult objects representing the search results
     */
    public Collection<PageSearchResult> search(String queryString) {
        searchResults = new ArrayList<>();

        try {
            Query query = new QueryParser("content", analyzer).parse(queryString);
            IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(index));
            TopDocs results = searcher.search(query, 10);

            List<String> titles = new ArrayList<>();
            List<String> contents = new ArrayList<>();

            for (ScoreDoc scoreDoc : results.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                String title = doc.get("title");
                String content = doc.get("content");

                if (!titles.contains(title)) {
                    titles.add(title);
                    contents.add(content);
                }
            }

            for (int i = 0; i < titles.size(); i++) {
                String formattedContent = titles.get(i) + " - " + contents.get(i);
                searchResults.add(new PageSearchResult(formattedContent));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    /**
     * Retrieves the StandardAnalyzer used for indexing and searching.
     *
     * @return the StandardAnalyzer object
     */
    public StandardAnalyzer getAnalyzer() {
        return analyzer;
    }

    /**
     * Sets the StandardAnalyzer used for indexing and searching.
     *
     * @param analyzer the StandardAnalyzer object
     */
    public void setAnalyzer(StandardAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * Retrieves the Lucene index directory.
     *
     * @return the Directory object representing the Lucene index
     */
    public Directory getIndex() {
        return index;
    }

    /**
     * Sets the Lucene index directory.
     *
     * @param index the Directory object representing the Lucene index
     */
    public void setIndex(Directory index) {
        this.index = index;
    }

    /**
     * Retrieves the HashMap storing the pages.
     *
     * @return the HashMap containing the pages
     */
    public HashMap<String, Page> getPages() {
        return pages;
    }

    /**
     * Sets the HashMap storing the pages.
     *
     * @param pages the HashMap containing the pages
     */
    public void setPages(HashMap<String, Page> pages) {
        this.pages = pages;
    }

    /**
     * Retrieves the list of search results.
     *
     * @return the list of PageSearchResult objects representing the search results
     */
    public List<PageSearchResult> getSearchResults() {
        return searchResults;
    }

    /**
     * Sets the list of search results.
     *
     * @param searchResults the list of PageSearchResult objects representing the search results
     */
    public void setSearchResults(List<PageSearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
