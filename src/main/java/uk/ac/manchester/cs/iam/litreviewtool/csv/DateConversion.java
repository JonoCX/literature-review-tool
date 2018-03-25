package uk.ac.manchester.cs.iam.litreviewtool.csv;

import com.univocity.parsers.conversions.Conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jonathan Carlton
 */
public class DateConversion implements Conversion<String, Date> {

    private SimpleDateFormat format;

    public DateConversion(String args) {
        this.format = new SimpleDateFormat(args);
    }

    public DateConversion(String[] args) {
        this.format = new SimpleDateFormat(args[0]);
    }

    @Override
    public Date execute(String input) {
        try {
            return format.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String revert(Date input) {
        return format.format(input);
    }
}
