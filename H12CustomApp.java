///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           Homework Organizer(List of homework assignments based on
//                  the date that the user wants)
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
///////////////////////////////// REFLECTION ///////////////////////////////////
//
// 1. Describe the problem you wrote the program to solve: The problem that the program was trying
// to solve was to write specific information about multiple homework assignments (including the
// name of the assignment, the subject of the homework and their due date in the current month) into
// the file "HomeworkList.txt" and then the file is read and a list of the assignments (with
// their subjects) gets printed out to the user when the user specifies for which DAY they want
// to see the list. For ex, if the user puts in 12 and there are 2 assignments that are due that
// day, those two assignments (with their subjects) will get printed to the user.
// 2. Why did you choose the method header for the read file method (e.g., return type,
// parameters, throws clause)? The method's return type is String because the method, after
// reading from the file, ends up generating a string of the desired homework list, which later
// gets printed out to the user. The parameters are: filename and chosenDay. The parameter
// "filename" is important because it specifies which file the method should read from.
// Additionally, the chosenDay is important because it shows the day for which the user wants
// to see the homework list. Therefore, it filters out the unessential homework entries.
// 3. Why did you choose the method header for the write file method (e.g., return type,
// parameters, throws clause)? The return type of this method is boolean because it indicates
// whether the writing operation was successful and whether the information about each assignment
// got written into the file successfully ("True"). The parameters are fileName, and String arrays
// of homework list and classes list and an integer array of due dates list. The fileName is
// necessary because it indicates which file the method should write into. And all the arrays
// are important because they provide the contents that should be written into the file in order
// of "homework list, class name, due date".
// 4. What are the biggest challenges you encountered: The biggest challenge that I faced was
// with the test cases! Without realizing it, I had the contents for the string arrays that
// were supposed to get printed out as ONE string (instead of multiple strings) so my test
// cases kept failing. However, it worked out at the end (after hours of frustration).
// 3. What did you learn from this assignment: I learned how to write to a file and read from the
// file and with this process provide specific/valuable information to the user.
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class contains the code for reading user's number of classes, names of homework assignments
 * and their corresponding class subjects and due dates and then writing these information into
 * the file HomeworkList.txt. The program then reads from the file and filters through the entries
 * to provide the user with the list of homework assignments that are due the day they want.
 *
 * @author Hasti Ghasemivaghar
 */
public class H12CustomApp {
    /**
     * This method contains the program that reads from the file and makes sure to only return the
     * list of homework assignments (and their class names) of which their due date is the one
     * that user asked for. The program also has appropriate try and catch statements to
     * handle a FileNotFoundException.
     * <p>
     * EXAMPLE:
     * If we had the 1st line form the file to be: essay, english, 12 and the
     * 2nd line to be: worksheet, math, 13; after the user puts in the number 12 in main
     * (chosenDay variable), the file is read and only the essay assignment is picked
     * and collected, and the method will return the statement "essay for english class".
     * <p>
     *
     * @param filename this is the file that the method should read from.
     * @param chosenDay this includes the day that the method uses to filter out assignment
     * entries and collect the appropriate assignments and subject information.
     * @return String content that includes the desired homework list with its subjects.
     */
    public static String readHomeworkToFile(String filename, int chosenDay) {
        File file = new File(filename);
        Scanner input = null;
        StringBuilder contents = new StringBuilder();
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String [] parts = line.split(", ");
                if (parts.length == 3) {
                    int dueDate = Integer.parseInt(parts[2]);
                    if (dueDate == chosenDay) {
                        contents.append(parts[0]).append(" for ").
                                append(parts[1]).append(" class").append("\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("readFile FileNotFoundException: " + e.getMessage());
        } finally {
            if (input != null) input.close();
        }
        return contents.toString();
    }

    /**
     * This method contains the program that writes the provided information about the homework
     * assignments into the file HomeworkList.txt in order of "assignment name, class name,
     * due date". The program also has appropriate try and catch statements to handle a
     * FileNotFoundException.
     * <p>
     * EXAMPLE:
     * If the homework list is: essay and worksheet, Classes list is: english and math and the
     * due dates list is: 12th, 12th, all of these will get written into the file as
     * 1st line: essay, english, 12 and the 2nd line: worksheet, math, 12.
     * <p>
     *
     * @param filename this is the filename that should be written into.
     * @param homeworkList this includes the string list of the assignments stored in the array.
     * @param classesList this includes the string list of the classes for which the homework
     * is assigned for (stored in the array)
     * @param dueDateList this includes the integer list of the due dates of the assignments
     * stored in the array.
     * @return boolean type, which indicates whether the writing operation to the file was
     * successful or not.
     */
    public static boolean writeHomeworkToFile(String filename, String[] homeworkList,
                                              String [] classesList, int[] dueDateList) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
            int len = homeworkList.length;
            for (int i = 0; i < len; i++) {
                String contents = homeworkList[i] + ", " + classesList[i] + ", " +
                        Integer.toString(dueDateList[i]);
                writer.println(contents);
            }

        } catch (FileNotFoundException e) {
            System.out.println("writeFile FileNotFoundException: " + e.getMessage());
            return false;
        }
        finally {
            if (writer != null) writer.close();
        }

        return true;
    }

    /**
     * The main method first prompts the user to type in the name of the file ("HomeworkList.txt")
     * and upon error, it will catch the NoSuchElementException. Once the correct name is put in,
     * the method will then prompt the user to input the number of classes, the names of the
     * homework assignments, classes, and their due date and will make sure that the correct input
     * is put in for each (ex. String type and integer type).
     * The program then calls the writeHomeworkToFile method so that all of these information that
     * was collected (mentioned above) will get written into the file.
     * The program then prompts the user to type in the day for which they want to see their
     * homework list and then calls the readHomeworkToFile to read from the file and return that
     * desired list which is then printed out for the user to see.
     *
     * @param args unused
     */
    public static void main(String[] args) {

        final String FILE_NAME = "HomeworkList.txt";

        Scanner scnr = new Scanner(System.in);
        String fileName = "";

        do {
            System.out.println("Enter the name of the file (" + FILE_NAME + "): ");
            try {
                fileName  = scnr.next();
            }
            catch (NoSuchElementException e) {
                System.out.println("There is no such element");
                break;
            }

            if (!fileName.equals(FILE_NAME)) {
                System.out.println("Invalid file name.");
            }
        } while (!fileName.equals(FILE_NAME));

        System.out.println("Please enter the number of classes (1-10): ");
        int numClasses = 0;

        while (true) {
            if (scnr.hasNextInt()) {
                numClasses = scnr.nextInt();
                if (numClasses < 1 || numClasses > 10) {
                    break;
                }
                break;
            } else {
                break;
            }
        }

        System.out.println("Enter the names of the Homework (as one word):");
        String[] homeworkList = new String[numClasses];
        for (int i = 0; i < homeworkList.length; i++) {
            if (scnr.hasNextInt()) {
                break;
            } else {
                homeworkList[i] = scnr.next().toLowerCase();
            }
        }


        System.out.println("Enter the classes in which the Homework is for:");
        String[] classesList = new String[numClasses];
        for (int i = 0; i < homeworkList.length; i++) {
            if (scnr.hasNextInt()) {
                break;
            }
            else {
            classesList[i] = scnr.next().toLowerCase();
            }
        }



        System.out.println("Enter the days (in DD) that each homework mentioned above is due:");
        int[] dueDateList = new int[numClasses];
        for (int i = 0; i < homeworkList.length; i++) {
            if (scnr.hasNextInt()) {
                dueDateList[i] = scnr.nextInt();
            } else {
                break;
            }
        }

        writeHomeworkToFile(fileName, homeworkList, classesList, dueDateList);

        System.out.println("Enter the day (DD) to get your homework list.");
        while (true) {
            if (scnr.hasNextInt()) {
                int chosenDay = scnr.nextInt();
                for (int i = 0; i < dueDateList.length; i++) {
                    if (chosenDay == dueDateList[i]) {
                    String desiredHomeworkList = readHomeworkToFile(fileName, chosenDay);
                    System.out.println("Your homework list for day " + chosenDay +
                            " this month:\n" + desiredHomeworkList);
                    break;
                    }
                    else {
                        System.out.println("Invalid Input!");
                        break;
                    }
                }
            }
            else {
                System.out.println("No value entered");
                break;
            }
            break;
        }
        scnr.close();

    }
}

