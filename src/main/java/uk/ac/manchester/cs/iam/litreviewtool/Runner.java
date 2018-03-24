package uk.ac.manchester.cs.iam.litreviewtool;

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

    void run() {
        if (isResume) {
            // resume code
        }
        else {
            // new session
        }
    }

}
