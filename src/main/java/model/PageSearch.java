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
import java.util.List;

public class PageSearch {
    private StandardAnalyzer analyzer;
    private Directory index;
    private List<Page> pages;
    private List<PageSearchResult> searchResults;

    public PageSearch(Collection<Page> pages) {
        this.analyzer = new StandardAnalyzer();
        this.index = new ByteBuffersDirectory();
        this.pages = new ArrayList<>(pages);

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (IndexWriter writer = new IndexWriter(index, config)) {
            for (Page page : this.pages) {
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

            for (ScoreDoc scoreDoc : results.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                String content = doc.get("content");
                searchResults.add(new PageSearchResult(content));
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

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public List<PageSearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<PageSearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
