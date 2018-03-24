package uk.ac.manchester.cs.iam.litreviewtool.models;

import com.univocity.parsers.annotations.Parsed;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Jonathan Carlton
 */
public class PaperOut implements Serializable {

    @Parsed(field = "title") private String title;
    @Parsed(field = "authors") private String author;
    @Parsed(field = "year") private String year;
    @Parsed(field = "abstract") private String abs;
    @Parsed(field = "keywords") private String keywords;
    @Parsed(field = "decisionMade") private LocalDateTime decisionMade;

    public PaperOut() { }

    public PaperOut(String title, String author, String year, String abs, String keywords, LocalDateTime decisionMade) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.abs = abs;
        this.keywords = keywords;
        this.decisionMade = decisionMade;
    }

    public static PaperOut valueOf(Paper paper, Instant now) {
        return new PaperOut(
                paper.getTitle(),
                paper.getAuthor(),
                paper.getYear(),
                paper.getAbs(),
                paper.getKeywords(),
                LocalDateTime.ofInstant(now, ZoneId.systemDefault())
        );
    }

    @Override
    public String toString() {
        return title + ", " + author + ", " + year + ", " + abs + ", " + keywords + ", " + decisionMade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaperOut paperOut = (PaperOut) o;

        if (!title.equals(paperOut.title)) return false;
        if (!author.equals(paperOut.author)) return false;
        if (!year.equals(paperOut.year)) return false;
        if (!abs.equals(paperOut.abs)) return false;
        if (!keywords.equals(paperOut.keywords)) return false;
        return decisionMade.equals(paperOut.decisionMade);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + year.hashCode();
        result = 31 * result + abs.hashCode();
        result = 31 * result + keywords.hashCode();
        result = 31 * result + decisionMade.hashCode();
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

    public LocalDateTime getDecisionMade() {
        return decisionMade;
    }

    public void setDecisionMade(LocalDateTime decisionMade) {
        this.decisionMade = decisionMade;
    }


}
