package uk.ac.manchester.cs.iam.litreviewtool;

import org.apache.commons.io.FilenameUtils;
import uk.ac.manchester.cs.iam.litreviewtool.csv.CsvParse;
import uk.ac.manchester.cs.iam.litreviewtool.models.InputInteruptException;
import uk.ac.manchester.cs.iam.litreviewtool.models.Paper;
import uk.ac.manchester.cs.iam.litreviewtool.models.PaperOut;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Jonathan Carlton
 */
public class Runner {

    private String file;
    private boolean isResume;

    Runner(String file, boolean isResume) {
        this.file = file;
        this.isResume = isResume;
    }

    void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To exit, enter any 'e'");
        TimeUnit.SECONDS.sleep(3);

        if (isResume) {
            // resume code
            List<PaperOut> previouslySaved = CsvParse.getPreviouslySaved(getFileName(file));
            List<Paper> papers = CsvParse.getPapers(file);
            this.commonTask(papers, previouslySaved);
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
                    throw new InputInteruptException();
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
        } catch (InputInteruptException e) {
            System.out.println("Process stopped. Saving papers...");
            CsvParse.resultsOut(saved, getFileName(file));
            System.out.println("Saved!");
        }
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
