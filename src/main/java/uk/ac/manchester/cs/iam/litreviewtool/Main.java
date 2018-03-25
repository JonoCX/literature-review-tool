package uk.ac.manchester.cs.iam.litreviewtool;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import java.io.File;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("file",  true, "location of the csv file on the system");
        options.addOption("resume", false, "resume the processing");

        String header = "The list of accepted program arguments";
        String footer = "Any further issues, report them to the author of the system";

        HelpFormatter helpFormatter = new HelpFormatter();

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cli = parser.parse(options, args);

            if (cli.hasOption("help") || cli.getArgList().isEmpty()) {
                helpFormatter.printHelp("literaturereviewtool", header, options, footer, true);
            }

            if (cli.hasOption("file") && !(cli.hasOption("resume"))) {
                // new session
                String fileLoc = cli.getOptionValue("file");

                if (!(new File(fileLoc).isFile())) {
                    throw new ParseException("File does not exist");
                }

                // run
                Runner run = new Runner(fileLoc, false);
                run.run();
            }

            if (cli.hasOption("file") && cli.hasOption("resume")) {
                // resume session
                String fileLoc = cli.getOptionValue("file");

                if (!(new File(fileLoc).isFile())) {
                    throw new ParseException("File does not exist");
                }

                Runner run = new Runner(fileLoc, true);
                run.run();
            }

        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        } catch (Exception ee) {
            System.err.println("Something went wrong. Reason: " + ee.getMessage());
            ee.printStackTrace();
        }
    }
}
