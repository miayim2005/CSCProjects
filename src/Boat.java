import java.io.Serializable;

/**
 *Boat in fleet
 * @author Mia Yim
 */
public class Boat implements Serializable {

    /**
     * Represents the types of boats
     */
    public enum BoatType {SAILING, POWER}

    private BoatType type;
    private String name;
    private int year;
    private String makeModel;
    private int length;
    private double purchasePrice;
    private double maintenanceExpense;

    /**
     * Initial value constructor
     * @param type from BoatType enum
     * @param name name of boat
     * @param year year made
     * @param makeModel name of model
     * @param length length in feet
     * @param purchasePrice price of boat
     */
    public Boat (BoatType type, String name, int year, String makeModel, int length, double purchasePrice) {
        this.type = type;
        this.name = name;
        this.year = year;
        this.makeModel = makeModel;
        this.length = length;
        this.purchasePrice = purchasePrice;
        this.maintenanceExpense = 0;
    }//end of Boat method

    /**
     * accessor method to retrieve name of boat
     * @return name of boat
     */
    public String getName() {
        return name;
    }

    /**
     * accessor method to retrieve purchase price
     * @return purchase price
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * accessor method to retrieve maintenance expense
     * @return maintenance expense
     */
    public double getMaintenanceExpense() {
        return maintenanceExpense;
    }

    /**
     * add expense to maintenance expense of a certain boat
     * @param expense amount of expense to add
     */
    public void addExpense(double expense) {

        double moneyLeft;

        if (maintenanceExpense + expense <= purchasePrice) {
            maintenanceExpense += expense;
            System.out.printf("Expense authorized, %.2f spent.\n", maintenanceExpense);
        }
        else {
            moneyLeft = purchasePrice - maintenanceExpense;
            System.out.printf("Expense not permitted, only $%.2f left to spend.\n", moneyLeft);
        }//end of if else statement
    }

    /**
     *  Produce printable information about the boat data
     * @return string with boat's type, name, year, model, length, purchase price, and expenses
     */
    @Override
    public String toString() {
        return String.format("    %-8s %-21s %-5d %-12s %-4s : Paid $ %-8.2f : Spent $ %-8.2f",
                type, name, year, makeModel, length + "'", purchasePrice, maintenanceExpense);
    }


}//end of boat class
