import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * Program records the information for a fleet of boats and saves it to a new file
 * @author Mia Yim
 * @see Boat
 */
public class FleetManagement {
    /**
     * Global Scanner object to use keyboard
     */
    private static final Scanner keyboard = new Scanner(System.in);
    private static final String FLEET_DATA_FILE = "FleetData.db";

    /**
     * The main method
     * @param args passed in from the command line
     */
    public static void main(String[] args)  {
        ArrayList<Boat> fleet = new ArrayList<>();
        boolean input;
        char inputChar;

        //read csv file from command line arguments
        try {
            if(args.length > 0){
                readFleetFile(args[0], fleet);
            }
            else{
                readFromSerialized(fleet);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        //Display welcome message
        System.out.println("Welcome to the Fleet Management System");
        System.out.println("--------------------------------------");

        //PAREX menu
        do{
            do{
                System.out.println();
                System.out.print("(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ");
                inputChar = keyboard.nextLine().toUpperCase().charAt(0);
                switch (inputChar) {
                    case 'P':
                        printFleet(fleet);
                        input = true;
                        break;
                    case 'A':
                        addBoat(fleet);
                        input = true;
                        break;
                    case 'R':
                        removeBoat(fleet);
                        input = true;
                        break;
                    case 'E':
                        expenseBoat(fleet);
                        input = true;
                        break;
                    case 'X':
                        saveFleetData(fleet);
                        System.out.println();
                        System.out.println("Exiting the Fleet Management System");
                        return;
                    default:
                        System.out.print("Invalid menu option, try again              : ");
                        input = false;
                        break;
                }
            } while (!input);

        } while(true);

    }//end of main method
//-----------------------------------------------------------------------------------------------------------------

    /**
     * read file from command line argument
     * @param fileName name of file
     * @param fleet ArrayList of boat data
     * @throws IOException file not found exception
     */
    private static void readFleetFile(String fileName,ArrayList<Boat> fleet) throws IOException {
        BufferedReader fromBufferedReader;
        fromBufferedReader = new BufferedReader(new FileReader(fileName));
        String line;


        line = fromBufferedReader.readLine();
        while (line != null) {

            //split line into data
            String[] data = line.split(",");
            Boat.BoatType type;
            String name;
            int year;
            String makeModel;
            int length;
            double purchasePrice;

            type = Boat.BoatType.valueOf(data[0].toUpperCase());
            name = data[1];
            year = Integer.parseInt(data[2]);
            makeModel = data[3];
            length = Integer.parseInt(data[4]);
            purchasePrice = Double.parseDouble(data[5]);
            Boat boat = new Boat(type, name, year, makeModel, length, purchasePrice);

            //add boat to ArrayList
            fleet.add(boat);
            line = fromBufferedReader.readLine();
        }//end of while loop

        //close file
        fromBufferedReader.close();

    }// end of readFleetFile method

    /**
     * On a non-initializing run, data is read from the serialized file
     * @param fleet ArrayList of boat data
     * @throws IOException file not found exception
     */
    private static void readFromSerialized(ArrayList<Boat> fleet) throws IOException {
        ObjectInputStream fromStream = null;
        Boat boat;

            try {
                fromStream = new ObjectInputStream(new FileInputStream(FLEET_DATA_FILE));
                while (true) {
                    try {
                        boat = (Boat)fromStream.readObject();
                        // Add it to the fleet
                        fleet.add(boat);
                    } catch (EOFException e) {
                        // End of file reached, break out of the loop
                        break;
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error: Class not found for object deserialization.");
                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            //close file
            finally {
                if (fromStream != null){
                    try{
                        fromStream.close();
                    }
                    catch(IOException e ){
                        System.out.println(e.getMessage());
                    }
                }
            }
    }

    /**
     * Save fleet data to new file
     * @param fleet ArrayList of boat data
     */
    private static void saveFleetData(ArrayList<Boat> fleet) {
        ObjectOutputStream toFile = null;
        int index;

        try {
            toFile = new ObjectOutputStream(new FileOutputStream(FLEET_DATA_FILE));
            for(index = 0; index < fleet.size(); index++) {
                toFile.writeObject(fleet.get(index));
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //close file
        finally {
            if (toFile != null){
                try{
                    toFile.close();
                }
                catch(IOException e ){
                    System.out.println(e.getMessage());
                }

            }
        }
    }//end of saveFleetData method

    private static void printFleet(ArrayList<Boat> fleet){

        double totalPaid;
        double totalSpent;
        int index;

        totalPaid = 0;
        totalSpent =0;

        System.out.println();
        System.out.println("Fleet report:");
        for (index = 0; index< fleet.size(); index++) {
            Boat boat = fleet.get(index);
            System.out.println(boat);
            totalPaid += boat.getPurchasePrice();
            totalSpent += boat.getMaintenanceExpense();
        }//end of for loop

        System.out.printf("    Total                                                  : Paid $ %-8.2f : Spent $ %-8.2f\n", totalPaid, totalSpent);

    }//end of printFleet method

    /**
     * add a boat to ArrayList
     * @param fleet ArrayList of boat data
     */
    private static void addBoat(ArrayList<Boat> fleet){
        String[] data;
        String input;
        Boat.BoatType type;
        String name;
        int year;
        String makeModel;
        int length;
        double purchasePrice;

        System.out.print("Please enter the new boat CSV data : ");
        input = keyboard.nextLine();
        //split line into array of data
        data = input.split(",");

        type = Boat.BoatType.valueOf(data[0].toUpperCase());
        name = data[1].trim();
        year = Integer.parseInt(data[2].trim());
        makeModel = data[3].trim();
        length = Integer.parseInt(data[4].trim());
        purchasePrice = Double.parseDouble(data[5].trim());

        // add new boat to ArrayList
        fleet.add(new Boat(type, name, year, makeModel, length, purchasePrice));

    }//end of addBoat method

    /**
     * remove boat from ArrayList
     * @param fleet ArrayList of boat data
     */
    private static void removeBoat(ArrayList<Boat> fleet){
        String input;
        Boat boat;

        System.out.print("Which boat do you want to remove? : ");
        input = keyboard.nextLine();

        //call find boat method
        boat = findBoat(input, fleet);

        if (boat != null){
            fleet.remove(boat);
        }
        else{
            System.out.println("Cannot find boat " + input);
        }//end of if else statement

    }// end of removeBoat method

    /**
     * Spend money on a particular boat
     * @param fleet ArrayList of boat data
     */
    private static void expenseBoat(ArrayList<Boat> fleet) {
        String input;
        Boat boat;
        double expense;

        System.out.print("Which boat do you want to spend on? : ");
        input = keyboard.nextLine();

        //call find boat method
        boat = findBoat(input,fleet);

        if (boat != null) {
            System.out.print("How much do you want to spend? : ");
            expense = Double.parseDouble(keyboard.nextLine());
            boat.addExpense(expense);
        }
        else {
            System.out.println("Cannot find boat " + input);
        }//end of if else statement

    }//end of expenseBoat method

    /**
     * method to find a boat by matching name
     * @param input user input of name of boat to find
     * @param fleet ArrayList of boat data
     * @return boat object if found, null if there is no match
     */
    private static Boat findBoat(String input, ArrayList<Boat> fleet) {
        int index;
        Boat boat;

        for (index = 0; index < fleet.size(); index++) {
                boat = fleet.get(index);
                if (boat.getName().equalsIgnoreCase(input)){
                    return boat;
                }
        }//end of for loop
            return null;

    }// end of findBoat method

    }//end of the FleetManagement class
