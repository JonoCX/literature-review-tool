package uk.ac.manchester.cs.iam.litreviewtool;

import org.apache.commons.io.FilenameUtils;
import uk.ac.manchester.cs.iam.litreviewtool.csv.CsvParse;
import uk.ac.manchester.cs.iam.litreviewtool.models.InputInterruptException;
import uk.ac.manchester.cs.iam.litreviewtool.models.LiteratureReviewException;
import uk.ac.manchester.cs.iam.litreviewtool.models.Paper;
import uk.ac.manchester.cs.iam.litreviewtool.models.PaperOut;

import java.time.Instant;
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
    private boolean isResume;

    Runner(String file, boolean isResume) {
        this.file = String.valueOf(Objects.nonNull(file));
        this.isResume = isResume;
    }

    void run() throws Exception {
        System.out.println("To exit, enter any 'e'");
        TimeUnit.SECONDS.sleep(3);

        if (isResume) {
            // resume code
            List<PaperOut> previouslySaved = CsvParse.getPreviouslySaved(getFileName(file));
            List<Paper> papers = CsvParse.getPapers(file);

            /*
                Get the index of the last saved paper.

                This could be improved, i.e. get the index of the last viewed paper, but
                this will do for the time being -- saves having auxiliary files/db.
             */
            Paper last;
            try {
                last = Paper.valueOf(previouslySaved.get(previouslySaved.size() - 1));
            } catch (IndexOutOfBoundsException e) {
                throw new LiteratureReviewException("Unable to parse the last paper saved.");
            }

            int index = papers.indexOf(last);
            if (index >= 0) {
                papers = papers.subList(index + 1, papers.size() - 1);
                this.commonTask(papers, previouslySaved);
            }
            else {
                throw new LiteratureReviewException("Could not find the previously saved paper in the supplied file.");
            }

        }
        else {
            // new session
            List<PaperOut> saved = new ArrayList<>();
            List<Paper> papers = CsvParse.getPapers(file);
            this.commonTask(papers, saved);
        }
    }

    private void commonTask(List<Paper> papers, List<PaperOut> saved) throws Exception {
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
                    continue;
                }

                if (decision.equals("y")) {
                    saved.add(PaperOut.valueOf(p, Instant.now()));
                }
            }
            System.out.println("Viewed all papers! Outputting the results file...");
            CsvParse.resultsOut(saved, getFileName(file));
            System.out.println("Saved!");
        } catch (InputInterruptException e) {
            System.out.println("Process stopped. Saving papers...");
            CsvParse.resultsOut(saved, getFileName(file));
            System.out.println("Saved!");
        }
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
