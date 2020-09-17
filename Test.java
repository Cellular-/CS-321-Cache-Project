import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        if (isArgsValid(args)) {
            run(args); 
        }
    }

    private static void run(String[] args) {
        int numCacheLevels = Integer.parseInt(args[0]);
        int cacheLevelOneMaxCapacity = Integer.parseInt(args[1]);
        int cacheLevelTwoMaxCapacity = args.length == 4 ? Integer.parseInt(args[2]) : 0;
        Cache<String> cacheLevelOne = new Cache<String>(cacheLevelOneMaxCapacity);
        Cache<String> cacheLevelTwo = new Cache<String>(cacheLevelTwoMaxCapacity);
        String filePath = args[args.length - 1]; // file path is always last argument

        try {
            Scanner lineScanner = new Scanner(new File(filePath));
            Scanner wordScanner;

            if (numCacheLevels == 1) {

            } else if (numCacheLevels == 2) {
                while(lineScanner.hasNext()) {
                    wordScanner = new Scanner(lineScanner.nextLine());
                    wordScanner.useDelimiter(" +");

                    while(wordScanner.hasNext()) {
                        String word = wordScanner.next();
                        if(cacheLevelOne.contains(word)) {
                            cacheLevelOne.addToHits(1);  // Increment # of cache1 hits

                            // Move hit data item to top of both cache.
                            cacheLevelOne.removeObject(word);
                            cacheLevelOne.addObject(word, Position.TOP);
                            cacheLevelTwo.removeObject(word);
                            cacheLevelTwo.addObject(word, Position.TOP);
                        } else {
                            if(cacheLevelTwo.contains(word)) {
                                cacheLevelTwo.addToHits(1); // Increment # of cache2 hits

                                // Add hit data item to the top of both cache
                                cacheLevelTwo.removeObject(word);
                                cacheLevelTwo.addObject(word, Position.TOP);

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
            }

            lineScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isArgsValid(String[] args) {
        boolean isValid = true;

        if(args.length < 3 || args.length > 4) {
            isValid = false;
            
            String usageMsg = "\nToo many arguments!\n"
                            + "\tUsage:\n"
                            + "\tjava Test 1 <cache size> <input textfile name>\n"
                            + "\tOR\n"
                            + "\tjava Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>\n";

            throw new IllegalArgumentException(usageMsg);
        }

        return isValid;
    }
}