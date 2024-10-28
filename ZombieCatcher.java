import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ZombieCatcher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the start time:");
        int startTime = scanner.nextInt();
        System.out.println("Enter the end time:");
        int endTime = scanner.nextInt();

        int potentialZombies = 0;

        // If files are provided as arguments, read from them
        if (args.length > 0) {
            for (String filename : args) {
                try {
                    File file = new File(filename);
                    Scanner fileScanner = new Scanner(file);
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] data = line.split(" ");
                        String name = data[0];
                        int arrivalTime = Integer.parseInt(data[1]);
                        int departureTime = Integer.parseInt(data[2]);

                        if (overlappingDayAndNightPeriods(startTime, endTime, arrivalTime, departureTime)) {
                            System.out.println(name + " needs to be quarantined.");
                            potentialZombies++;
                        }
                    }
                    fileScanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("WARNING: " + filename + " not found.");
                }
            }
        } else {
            potentialZombies = getVisitors(startTime, endTime);
        }

        System.out.println("Number of potential zombies: " + potentialZombies);
        scanner.close();
    }

    public static int getVisitors(int startTime, int endTime) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of visitors:");
        int numberOfVisitors = scanner.nextInt();
        int potentialZombies = 0;

        for (int i = 0; i < numberOfVisitors; i++) {
            System.out.println("Enter the visitor's name:");
            String name = scanner.next();
            System.out.println("Enter the arrival time:");
            int arrivalTime = scanner.nextInt();
            System.out.println("Enter the departure time:");
            int departureTime = scanner.nextInt();

            if (overlappingDayAndNightPeriods(startTime, endTime, arrivalTime, departureTime)) {
                System.out.println(name + " needs to be quarantined.");
                potentialZombies++;
            } else {
                System.out.println(name + " does not need to be quarantined.");
            }
        }

        return potentialZombies;
    }

    public static boolean overlappingPeriods(int start1, int end1, int start2, int end2) {
        return start1 < end2 && start2 < end1; // Check if periods overlap
    }

    // Additional method for question 4 - Handling overnight periods
    public static boolean overlappingDayAndNightPeriods(int start1, int end1, int start2, int end2) {
        // Handles cases where either period might cross midnight
        if (start1 < end1 && start2 < end2) {
            // Neither period crosses midnight
            return overlappingPeriods(start1, end1, start2, end2);
        } else if (start1 >= end1 && start2 < end2) {
            // First period crosses midnight, second does not
            return overlappingPeriods(start1, 24, start2, end2) || overlappingPeriods(0, end1, start2, end2);
        } else if (start1 < end1 && start2 >= end2) {
            // Second period crosses midnight, first does not
            return overlappingPeriods(start1, end1, start2, 24) || overlappingPeriods(start1, end1, 0, end2);
        } else {
            // Both periods cross midnight
            return overlappingPeriods(start1, 24, start2, 24) || overlappingPeriods(0, end1, 0, end2);
        }
    }
}
