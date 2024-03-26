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

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;

/**
 * Implements a page indexing and searching mechanism using Apache Lucene.
 * This class encapsulates the functionality to index provided pages and perform searches
 * over these indexed pages.
 */

public class PageSearch {
    private StandardAnalyzer analyzer; // The analyzer used for indexing and searching
    private Directory index; // The Lucene index directory
    private HashMap<String, Page> pages; // HashMap to store pages
    private List<PageSearchResult> searchResults; // List to store search results
    final int maxResults = 10; // Maximum number of search results to return

    /**
     * Initializes a new instance of PageSearch, indexing the provided pages.
     * This constructor sets up the analyzer, creates a Lucene index in memory, and indexes
     * the provided pages by reading their content from specified file paths.
     *
     * @param pages A HashMap mapping page identifiers to Page objects. Each page is indexed
     *              by its title and content for search operations.
     */
    public PageSearch(HashMap<String, Page> pages) {
        this.analyzer = new StandardAnalyzer();
        this.index = new ByteBuffersDirectory();
        this.pages = pages;

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // Indexing the pages
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
     * @param content the address of the txt file of the page
     * @throws IOException if an I/O error occurs
     */
    public void addDoc(IndexWriter w, String title, String content) throws IOException {
        // Reading the content of the page from the specified file path
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(content))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }

        String contentString = contentBuilder.toString();

        // Splitting content into paragraphs to index each separately for more granular search results
        String[] paragraphs = contentString.split("\\n\\n+");

        for (String paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) { // Ignore empty paragraphs
                Document doc = new Document();
                doc.add(new StringField("title", title, Field.Store.YES));
                doc.add(new TextField("content", paragraph, Field.Store.YES));
                w.addDocument(doc);
            }
        }
    }

    /**
     * Searches the indexed pages for paragraphs matching the given query string. This method
     * parses the query string into a Lucene query, performs the search, and collects up to a
     * predefined number of top scoring hits as search results.
     *
     * @param queryString The user's query string to search for in the indexed content.
     * @return A collection of PageSearchResult objects, each representing a matching paragraph
     * including its title and a snippet of content containing the search query.
     */

    public Collection<PageSearchResult> search(String queryString) {
        searchResults = new ArrayList<>();

        try {
            // Parsing the query string to handle complex search queries like phrases or wildcards
            Query query = new QueryParser("content", analyzer).parse(queryString);
            IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(index));
            TopDocs results = searcher.search(query, maxResults);

            List<String> titles = new ArrayList<>();
            List<String> contents = new ArrayList<>();

            // Collecting search results
            for (ScoreDoc scoreDoc : results.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                String title = doc.get("title");
                String content = doc.get("content");

                titles.add(title);
                contents.add(content);
            }

            // Formatting search results
            for (int i = 0; i < titles.size(); i++) {
                String formattedContent = "Title: " + titles.get(i) + "\n" + contents.get(i) + "\n";
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
