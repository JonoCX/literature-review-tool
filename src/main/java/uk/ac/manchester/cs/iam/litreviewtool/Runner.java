package uk.ac.manchester.cs.iam.litreviewtool;

import org.apache.commons.io.FilenameUtils;
import uk.ac.manchester.cs.iam.litreviewtool.csv.CsvParse;
import uk.ac.manchester.cs.iam.litreviewtool.models.InputInterruptException;
import uk.ac.manchester.cs.iam.litreviewtool.models.LiteratureReviewException;
import uk.ac.manchester.cs.iam.litreviewtool.models.Paper;
import uk.ac.manchester.cs.iam.litreviewtool.models.PaperOut;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Jonathan Carlton
 */
class Runner {

    private String file;
    private String fileName;
    private boolean isResume;

    Runner(String file, boolean isResume) {
        this.file = file;
        this.fileName = this.getFileName(this.file);
        this.isResume = isResume;

        if (isResume) { // check that the previous files exists.
            // there is a presumption that on resumption, there will be both saved and discarded files.
            // make this clear in the read me. Or just automatically make the files when a new session is started?

        }
    }

    void run() throws Exception {
        System.out.println("To exit, enter any 'e'");
        TimeUnit.SECONDS.sleep(3);

        if (isResume) {
            // resume code
            List<Paper> papers = CsvParse.getPapers(file);
            List<PaperOut> previouslySaved = CsvParse.getPreviouslySaved(fileName, false);
            List<PaperOut> previouslyDiscarded = CsvParse.getPreviouslySaved(fileName, true);

            // get the index of the last saved paper and the last discarded paper, compare them and whichever is
            // largest becomes our starting point for the resumption.
            Paper lastSaved, lastDiscarded;
            try {
                lastSaved = Paper.valueOf(previouslySaved.get(previouslySaved.size() - 1));
                lastDiscarded = Paper.valueOf(previouslyDiscarded.get(previouslyDiscarded.size() - 1));
            } catch (IndexOutOfBoundsException e) {
                throw new LiteratureReviewException("Unable to parse the saved or discarded files.");
            }

            int indexSaved = papers.indexOf(lastSaved);
            int indexDiscarded = papers.indexOf(lastDiscarded);
            if (indexSaved >= 0 || indexDiscarded >= 0) {
                // presumption that some papers will have been saved and discarded on resumption.
                int biggest = (indexSaved >= indexDiscarded) ? indexSaved : indexDiscarded;
                papers = papers.subList(biggest + 1, papers.size() - 1);
                this.commonTask(papers, previouslySaved, previouslyDiscarded);
            }
            else {
                throw new LiteratureReviewException("Could not find the previously saved paper in the supplied file.");
            }

        }
        else {
            // new session
            List<PaperOut> saved = new ArrayList<>();
            List<PaperOut> discarded = new ArrayList<>();
            List<Paper> papers = CsvParse.getPapers(file);
            this.commonTask(papers, saved , discarded);
        }
    }

    private void commonTask(List<Paper> papers, List<PaperOut> saved, List<PaperOut> discarded) throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            String decision;
            for (Paper p : papers) {
                System.out.println(p.getTitle() + " : [" + p.getKeywords() + "]");
                System.out.println("y / n / e:" );
                decision = scanner.nextLine();

                if (decision.equals("e")) {
                    throw new InputInterruptException();
                }

                if (decision.equals("n")) {
                    discarded.add(PaperOut.valueOf(p, this.getDateTimeInstant()));
                }

                if (decision.equals("y")) {
                    saved.add(PaperOut.valueOf(p, this.getDateTimeInstant()));
                }
            }
            System.out.println("Viewed all papers! Outputting the results file...");
            CsvParse.resultsOut(saved, fileName, false);
            CsvParse.resultsOut(discarded, fileName, true);
            System.out.println("Saved!");
        } catch (InputInterruptException e) {
            System.out.println("Process stopped. Saving papers...");
            CsvParse.resultsOut(saved, fileName, false);
            CsvParse.resultsOut(discarded, fileName, true);
            System.out.println("Saved!");
        }
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private String getDateTimeInstant() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

    private boolean checkSamePreviousFile() {
        // todo
        return true;
    }

    private String getFileName(String file) {
        String result = "";

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            String[] split = file.split(Pattern.quote("\\"));
            result = FilenameUtils.removeExtension(split[split.length - 1]);
        }

        if (os.contains("mac")) {
            String[] split = file.split(Pattern.quote("/"));
            result = FilenameUtils.removeExtension(split[split.length - 1]);
        }

        return result;
    }

}
