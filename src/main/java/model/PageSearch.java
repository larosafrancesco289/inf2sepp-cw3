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

public class PageSearch {
    private StandardAnalyzer analyzer;
    private Directory index;
    private HashMap<String, Page> pages;
    private List<PageSearchResult> searchResults;

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

    public void addDoc(IndexWriter w, String title, String content) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("title", title, Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        w.addDocument(doc);
    }

    // TO DO: Need formatting for search results
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

    public HashMap<String, Page> getPages() {
        return pages;
    }

    public void setPages(HashMap<String, Page> pages) {
        this.pages = pages;
    }

    public List<PageSearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<PageSearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
