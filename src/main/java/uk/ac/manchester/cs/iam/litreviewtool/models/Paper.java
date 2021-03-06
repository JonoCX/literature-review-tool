package uk.ac.manchester.cs.iam.litreviewtool.models;

import com.univocity.parsers.annotations.LowerCase;
import com.univocity.parsers.annotations.Parsed;

import java.io.Serializable;

/**
 * @author Jonathan Carlton
 */
public class Paper implements Serializable {

    @Parsed(field = "title") private String title;
    @Parsed(field = "authors") private String author;
    @Parsed(field = "year") private String year;
    @Parsed(field = "abstract") private String abs;
    @Parsed(field = "keywords") private String keywords;

    public Paper() { }

    public Paper(String title, String author, String year, String abs, String keywords) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.abs = abs;
        this.keywords = keywords;
    }

    public static Paper valueOf(PaperOut paper) {
        return new Paper(
                paper.getTitle(),
                paper.getAuthor(),
                paper.getYear(),
                paper.getAbs(),
                paper.getKeywords()
        );
    }

    @Override
    public String toString() {
        return title + ", " + author + ", " + year + ", " + abs + ", " + keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paper paper = (Paper) o;

        return title.equals(paper.title);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
