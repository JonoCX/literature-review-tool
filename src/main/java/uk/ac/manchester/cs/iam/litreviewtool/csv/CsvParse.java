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

    private static final String[] OUTPUT_OPTIONS = {
            "results_",
            "discarded_",
            ".csv"
    };

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

    public static void resultsOut(List<PaperOut> papers, String orgFileName, boolean isDiscarded) throws IOException {
        BufferedWriter bufferedWriter;

        if (isDiscarded) {
            bufferedWriter = new BufferedWriter(new FileWriter(
                    OUTPUT_OPTIONS[1] +
                            orgFileName +
                            OUTPUT_OPTIONS[2])
            );
        }
        else {
            bufferedWriter = new BufferedWriter(new FileWriter(
                    OUTPUT_OPTIONS[0] +
                            orgFileName +
                            OUTPUT_OPTIONS[2])
            );
        }

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

    public static List<PaperOut> getPreviouslySaved(String orgFileName, boolean isDiscarded) throws IllegalStateException {
        BeanListProcessor<PaperOut> rowProcessor = new BeanListProcessor<>(PaperOut.class);
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);

//        rowProcessor.convertFields(new DateConversion("yyyy-MM-dd HH:mm")).set("decisionMade");
        rowProcessor.convertFields(new DateConversion("yyyy-MM-dd HH:mm"));
//        rowProcessor.convertFields(Conversions.toDate("yyyy-MM-dd HH:mm")).set("decisionMade");

        CsvParser parser = new CsvParser(parserSettings);
        try {
            if (isDiscarded) {
                parser.parse(new InputStreamReader(new FileInputStream(OUTPUT_OPTIONS[1] + orgFileName + OUTPUT_OPTIONS[2])));
            }
            else {
                parser.parse(new InputStreamReader(new FileInputStream(OUTPUT_OPTIONS[0] + orgFileName + OUTPUT_OPTIONS[2])));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Unable to read input", e);
        }

        return rowProcessor.getBeans();
    }
}














