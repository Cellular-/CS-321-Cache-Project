/**
 * Tests the Cache by configuring the cache levels based on the
 * user argument. Prints cache results at the end such as number
 * of hit, references, and hit ratio.
 * 
 * @author Steven Lineses
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
    private static int numCacheLevels;
    private static int cacheLevelOneMaxCapacity;
    private static int cacheLevelTwoMaxCapacity;
    private static Cache<String> cacheLevelOne;
    private static Cache<String> cacheLevelTwo;
    private static String filePath; // file path is always last argument
    private static String[] commandLineArgs;

    public static void main(String[] args) {
        commandLineArgs = args;
        run();
    }

    private static void init() {
        isArgsValid(commandLineArgs);
        numCacheLevels = Integer.parseInt(commandLineArgs[0]);
        cacheLevelOneMaxCapacity = Integer.parseInt(commandLineArgs[1]);
        cacheLevelTwoMaxCapacity = commandLineArgs.length == 4 ? Integer.parseInt(commandLineArgs[2]) : 0;
        cacheLevelOne = new Cache<String>(cacheLevelOneMaxCapacity);
        cacheLevelTwo = new Cache<String>(cacheLevelTwoMaxCapacity);
        filePath = commandLineArgs[commandLineArgs.length - 1]; // file path is always last argument
    }

    private static void run() {
        init();

        try {
            Scanner lineScanner = new Scanner(new File(filePath));
            Scanner wordScanner;

            if (numCacheLevels == 1) {
                while (lineScanner.hasNext()) {
                    wordScanner = new Scanner(lineScanner.nextLine());
                    wordScanner.useDelimiter(" +");

                    while (wordScanner.hasNext()) {
                        String word = wordScanner.next();
                        if (cacheLevelOne.search(word)) {
                            cacheLevelOne.moveToTop(word);
                        } else {
                            cacheLevelOne.addObject(word, Position.TOP);
                        }
                    }
                }
            } else if (numCacheLevels == 2) {
                while (lineScanner.hasNext()) {
                    wordScanner = new Scanner(lineScanner.nextLine());
                    wordScanner.useDelimiter(" +");

                    while (wordScanner.hasNext()) {
                        String word = wordScanner.next();
                        if (cacheLevelOne.search(word)) {
                            // Move hit data item to top of both cache.
                            cacheLevelOne.moveToTop(word);
                            cacheLevelTwo.moveToTop(word);
                        } else {
                            if (cacheLevelTwo.search(word)) {
                                // Add hit data item to the top of both cache
                                cacheLevelTwo.moveToTop(word);

                                // Add data item to top of cache1
                                cacheLevelOne.addObject(word, Position.TOP);
                            } else {
                                // Add data item top of both cache
                                cacheLevelOne.addObject(word, Position.TOP);
                                cacheLevelTwo.addObject(word, Position.TOP);
                            }
                        }
                    }
                }

                // Write some code to print the results
            }

            lineScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isArgsValid(String[] args) {
        boolean isValid = true;

        if (args.length < 3 || args.length > 4) {
            isValid = false;

            String usageMsg = "\nToo many arguments!\n" + "\tUsage:\n"
                    + "\tjava Test 1 <cache size> <input textfile name>\n" + "\tOR\n"
                    + "\tjava Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>\n";

            throw new IllegalArgumentException(usageMsg);
        }

        return isValid;
    }
}