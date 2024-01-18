///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           Homework Organizer(List of Homework based on the date that the user wants)
// Course:          CS 200, Fall, and 2023
//
// Author:          Hasti Ghasemivaghar
// Email:           hghasemivagh@wisc.edu
// Lecturer's Name: Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// ChatGPT; Helped with the misplacement of the do-while loop
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is the test bench that contains testing methods for the H12CustomApp class.
 * The createTestDataFile and readTestDataFile are private testing methods intended to
 * be used within the test cases.
 *
 * All the test cases within the testH12CustomApp method should be changed to test the
 * methods in your H12CustomApp class.
 *
 * @author Jim Williams
 * @author Hasti Ghasemivaghar
 */
public class TestH12CustomApp {

    /**
     * This method runs the selected tests.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        testH12CustomApp();
    }

    /**
     * This is a testing method to create a file with the specified name and fileContents
     * to be used by other testing methods. On a FileNotFoundException a stack trace is printed and
     * then returns.
     *
     * @param testDataFilename The filename of the testing file to create.
     * @param fileContents     The data to put into the file.
     */
    private static void createTestDataFile(String testDataFilename, String fileContents) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(testDataFilename);
            writer.print(fileContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    /**
     * This is a testing method to read and return the entire contents of the specified file to
     * be used soley by other testing methods.
     * On a FileNotFoundException a stack trace is printed and then "" returned.
     *
     * @param dataFilename The name of the file to read.
     * @return The contents of the file or "" on error.
     */
    private static String readTestDataFile(String dataFilename) {
        File file = new File(dataFilename);
        Scanner input = null;
        String contents = "";
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                contents += input.nextLine() + "\n"; //assuming all lines end with newline
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (input != null) input.close();
        }
        return contents;
    }

    /**
     * Tests that the H12CustomApp read input and write output methods handle
     * the cases described in their method header comments.
     *
     * @return true for passing all testcases, false otherwise
     */
    public static boolean testH12CustomApp() {
        boolean error = false;

        {  //test that a file with a few lines of text can be read correctly

            String fileToRead = "testRead.txt";
            String fileContents = "essay, eng, 12\nreport, sci, 12\nworksheet, math, 13\n";
            createTestDataFile(fileToRead, fileContents);

            String actualContents = H12CustomApp.readHomeworkToFile(fileToRead, 12);
            String expectedContents = "essay for eng class\nreport for sci class\n";


            if ( !actualContents.equals(expectedContents)) {
                error = true;
                System.out.println("readFile 1) expected:" + expectedContents
                        + " actual: " + actualContents);
            } else {
                System.out.println("readFile 1) success");
                //since the test succeeded, remove the temporary file created with our testing
                // method
                File file = new File(fileToRead);
                file.delete();
            }
        }

        { //test that an invalid file returns "" for content.
            //makes sure the file doesn't exist by deleting it if it does.
            String fileToRead = "fileThatShouldNotExist";
            File file = new File(fileToRead);
            if ( file.exists()) {
                file.delete();
            }

            String expectedContents = "";
            String actualContents = H12CustomApp.readHomeworkToFile(fileToRead, 12);

            if ( !actualContents.equals(expectedContents)) {
                error = true;
                System.out.println("readFile 2) expected:" + expectedContents
                        + " actual: " + actualContents);
            } else {
                System.out.println("readFile 2) success");
            }
        }

        { //test that contents are correctly written to the specified file.

            String fileNameToWrite = "testWrite.txt";
            String[] homeworkList = {"essay", "report", "worksheet"};
            String [] classesList = {"eng", "sci", "math"};
            int[] dueDateList = {12, 12, 13};
            H12CustomApp.writeHomeworkToFile(fileNameToWrite, homeworkList,
                    classesList, dueDateList);

            String actualContents = readTestDataFile(fileNameToWrite);
            String expectedContents = "essay, eng, 12\nreport, sci, 12\nworksheet, math, 13\n";

            //checks if the contents are the same
            if ( !actualContents.equals(expectedContents)) {
                error = true;
                System.out.println("writeFile 3) expected:" + expectedContents
                        + " actual: " + actualContents);
            } else {
                System.out.println("writeFile 3) success");
                //since the test succeeded, remove the temporary testing file.
                File file = new File(fileNameToWrite);
                file.delete();
            }
        }

        { //test that an invalid file returns "" for content.
            String fileNameToWrite = "missingDirectory/fileThatShouldNotExist";
            boolean expectedResult = false;

            //check that the directory doesn't exist, since we want writeFile to handle the
            // exception when it tries to write the file to that non-existing directory.
            File file = new File(fileNameToWrite);
            if ( file.getParentFile().exists()) {
                error = true;
                System.out.println("writeFile 4) The directory: " + file.getParentFile().getName()
                        + " should not exist for this test to run correctly.");
            } else {
                System.out.println("writeFile 4) success");
            }
            String[] randomHomework = {"some homework"};
            String [] randomClasses = {"some class"};
            int [] randomDates = {12};
            boolean actualResult = H12CustomApp.writeHomeworkToFile(fileNameToWrite,
                    randomHomework, randomClasses, randomDates);

            if ( actualResult != expectedResult) {
                error = true;
                System.out.println("writeFile 5) expected:" + expectedResult
                        + " actual: " + actualResult);
            } else {
                System.out.println("writeFile 5) success");
            }
        }

        if (error) {
            System.out.println("\nTestH12CustomApp failed");
            return false;
        } else {
            System.out.println("\nTestH12CustomApp passed");
            System.out.println("There may be output from the methods being tested.");
            return true;
        }
    }
}
