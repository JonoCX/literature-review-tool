package uk.ac.manchester.cs.iam.litreviewtool.csv;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import uk.ac.manchester.cs.iam.litreviewtool.models.Paper;
import uk.ac.manchester.cs.iam.litreviewtool.models.PaperOut;

import java.io.*;
import java.util.List;

/**
 * @author Jonathan Carlton
 */
public class CsvParse {

    public static List<Paper> getPapers(String file) throws IllegalStateException {
        BeanListProcessor<Paper> rowProcessor = new BeanListProcessor<>(Paper.class);
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(parserSettings);
        try {
            parser.parse(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Unable to read input", e);
        }

        return rowProcessor.getBeans();
    }

    public static void resultsOut(List<PaperOut> papers, String orgFileName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("results_" + orgFileName + ".csv"));

        CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.setRowWriterProcessor(new BeanWriterProcessor<>(PaperOut.class));

        CsvWriter writer = new CsvWriter(bufferedWriter, writerSettings);
        writer.writeHeaders();

        for (PaperOut po : papers) {
            writer.processRecord(po);
        }

        writer.flush();
        writer.close();
    }

    public static List<PaperOut> getPreviouslySaved(String orgFileName) throws IllegalStateException {
        BeanListProcessor<PaperOut> rowProcessor = new BeanListProcessor<>(PaperOut.class);
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(parserSettings);
        try {
            parser.parse(new InputStreamReader(new FileInputStream("results_" + orgFileName + ".csv")));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Unable to read input", e);
        }

        return rowProcessor.getBeans();
    }
}














