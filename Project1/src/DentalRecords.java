import java.util.Scanner;
//==========================================================================================

/**
 * Program records the teeth for one Florida family
 * @author Mia Yim
 *
 */
public class DentalRecords {
    /**
     * Global Scanner object to use keyboard
     */
    private static final Scanner keyboard = new Scanner(System.in);
//CONSTANTS
    private static final int MAX_FAM = 6;
    private static final int MIN_FAM = 0;
    private static final int TEETH = 8;

    /**
     * The main method
     * @param args Passed in from the command line
     */
    public static void main(String[] args) {
        int famNumber;
        int names;
    //variables for iterating array teethData
        int index;
        int row;
        int tooth;

        char inputChar;
        boolean valid;
        boolean input;
        String rowOfTeeth;
        char [][][] teethData;
        String [] famNames;
        String [] mouth = {"uppers","lowers"};



    //Welcome statement
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

    //Get number of members
        System.out.print("Please enter number of people in the family : ");
        do {
            famNumber = keyboard.nextInt();

            if (famNumber <= MIN_FAM || famNumber > (MAX_FAM)) {
                System.out.print("Invalid number of people, try again         : ");
            }
            else{
                break;
            }
        } while (true);

    //Collect names for each family member

        famNames = new String [famNumber];
        teethData = new char[famNumber][2][];
        names =0;

            for (index = 0; index < famNumber; index++) {
            System.out.print("Please enter the name for family member "+ (names+1) +"   : ");
            famNames[names] = keyboard.next();

                for (row = 0; row < 2; row++) {
                    System.out.print("Please enter the " + mouth[row] + " for " + famNames[index] + "       : ");


                   do {
                       rowOfTeeth = keyboard.next().toUpperCase();
                       if (rowOfTeeth.length() > TEETH) {
                           System.out.print("Too many teeth, try again                   : ");
                           valid = false;

                       }
                       else if (!(containsIBM (rowOfTeeth))) {
                           System.out.print("Invalid teeth types, try again              : ");
                           valid = false;
                       }


                       else {
                           valid = true;
                       }

                   } while (!valid);

                    teethData [index][row] = new char [rowOfTeeth.length()];
                    for (tooth = 0; tooth < rowOfTeeth.length(); tooth++) {
                        teethData[index][row][tooth] = rowOfTeeth.charAt(tooth);

                    }

                }

             names++;
            }


//PERX menu
        do{
            System.out.println();
            System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
            inputChar = keyboard.next().toUpperCase().charAt(0);

            do{
                switch (inputChar) {
                    case 'P':
                        printTeeth(famNames, teethData);
                        input = true;
                        break;
                    case 'E':
                        extractTeeth(famNames, teethData);
                        input = true;
                        break;
                    case 'R':
                        rootTeeth(teethData);
                        input = true;
                        break;
                    case 'X':
                        System.out.println();
                        System.out.println("Exiting the Floridian Tooth Records :-)");
                        return;
                    default:
                        System.out.print("Invalid menu option, try again              : ");
                        inputChar = keyboard.next().toUpperCase().charAt(0);
                        input = false;
                        break;
                }
            } while (!input);

        } while(true);

    }//end of the main class

//--------------------------------------------------------------------------------------------------------------------

    /**
     * Check to see if a row of teeth contains valid characters
     * @param rowOfTeeth teeth types
     * @return true or false boolean if the row of teeth contain only I, B, or M
     */

private static boolean containsIBM (String rowOfTeeth) {
    String allowedChars = "IBM";
    int index;
    for (index = 0; index < rowOfTeeth.length(); index++) {
        if (allowedChars.indexOf(rowOfTeeth.charAt(index)) < 0) {
            return false;

        }
    }
    return true;
}// end of containsIMB class

//--------------------------------------------------------------------------------------------------------------------

    /**
     * Prints family's teeth (uppers and lowers)
     * @param famNames array of family's names
     * @param teethData array of teeth type for each family member
     */
    private static void printTeeth (String [] famNames, char [][][] teethData){
    int index;
    int teeth;

    System.out.println();

    for (index = 0; index <famNames.length; index++) {
        System.out.println(famNames[index]);
        System.out.print("  Uppers:  ");

        for(teeth = 0; teeth < teethData[index][0].length; teeth++){
            System.out.print((teeth+1) + ":" + teethData[index][0][teeth] + "  ");

        }
        System.out.println();
        System.out.print("  Lowers:  ");
        for(teeth = 0; teeth < teethData[index][1].length; teeth++){
            System.out.print((teeth+1) + ":" + teethData[index][1][teeth] + "  ");

        }
        System.out.println();
        System.out.println();
    }
}// end of the printTeeth class

// --------------------------------------------------------------------------------------------------------------------

    /**
     * Removes a tooth and changes it to missing
     * @param famNames array of family's names
     * @param teethData array of teeth type for each family member
     */
    private static void extractTeeth (String [] famNames, char [][][] teethData){
    Scanner keyboard = new Scanner(System.in);
    String famMember;
    String row;
    int tooth;
    int layer;
    int index;
    boolean valid;

//Select family member
    System.out.print("Which family member                         : ");
    do {
        famMember = keyboard.next();
        valid = false;
        for (index = 0; index < famNames.length; index++) {
            if (famMember.equalsIgnoreCase(famNames[index])) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            System.out.print("Invalid family member, try again            : ");
        }
    } while (!valid);

//Select upper or lower
    System.out.print("Which tooth layer (U)pper or (L)ower        : ");
    do {
        row = keyboard.next();
        if (row.equalsIgnoreCase("U")) {
            layer = 0;
        }
        else if (row.equalsIgnoreCase("L")){
            layer = 1;
        }
        else{
            System.out.print("Invalid layer, try again                    : ");
            layer = 2;
        }

    } while (layer == 2);

//Select tooth number
    System.out.print("Which tooth number                          : ");

    do {
        tooth = (keyboard.nextInt() -1);
        if (tooth >= 1 && tooth < teethData[index][layer].length) {
            if (teethData[index][layer][tooth] == 'M') {
                System.out.print("Missing tooth, try again                    : ");
                valid = false;
            }
            else {
                teethData[index][layer][tooth] = 'M';
                valid = true;
            }

        }
        else {
            System.out.print("Invalid tooth number, try again             : ");
            valid = false;
        }

    } while(!valid);

}//end of extractTeeth class

// --------------------------------------------------------------------------------------------------------------------

    /**
     * Finds the roots of each tooth using the quadratic equation Ix2 + Bx - M
     * @param teethData array of teeth type for each family member
     */
    private static void rootTeeth ( char [][][] teethData){
        double totalI;
        double totalB;
        double totalM;
        double root1;
        double root2;
        int member;
        int row;
        int tooth;

    //calculate total IBM
    totalI =0;
    totalB =0;
    totalM =0;
    for (member = 0; member < teethData.length; member++) {
        for (row = 0; row < teethData[member].length ; row++){
            for (tooth = 0; tooth < teethData[member][row].length; tooth++){
                switch(teethData[member][row][tooth]){
                    case 'I':
                        totalI++;
                        break;
                    case 'B':
                        totalB++;
                        break;
                    case 'M':
                        totalM++;
                        break;
                }
            }

        }
    }//end of calculating totals

    root1 = (-totalB + Math.sqrt(totalB*totalB + (4 * totalI * totalM)))/ (2*totalI);
    root2 = (-totalB - Math.sqrt(totalB*totalB + (4 * totalI * totalM)))/ (2*totalI);

    System.out.printf("One root canal at     %.2f%n",root1);
    System.out.printf("Another root canal at %.2f%n",root2);

}


}//end of the DentalRecords class
