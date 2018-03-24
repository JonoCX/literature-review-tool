package uk.ac.manchester.cs.iam.litreviewtool;

import org.apache.commons.cli.*;
import uk.ac.manchester.cs.iam.litreviewtool.csv.CsvParse;
import uk.ac.manchester.cs.iam.litreviewtool.models.Paper;

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
            }

        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        } catch (Exception ee) {
            System.err.println("Something went wrong. Reason: " + ee.getMessage());
        }

//        String file = "E:\\Dropbox (The University of Manchester)\\shared-phd-folder\\literature-review-data\\science-direct-data.csv";
//        List<Paper> res = CsvParse.getPapers(file);
//
////        String sep = "\\";
////        String[] val = file.split(Pattern.quote(sep));
//
//        List<Paper> sub = res.subList(0, 20);
//        for (Paper p : sub)
//            System.out.println(p.getTitle());

    }
}
