package uk.ac.manchester.cs.iam.litreviewtool.csv;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import uk.ac.manchester.cs.iam.litreviewtool.models.Paper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

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
}
