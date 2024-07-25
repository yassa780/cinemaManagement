import com.sun.security.jgss.GSSUtil;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static int[][] seatingPlan = new int[3][16];
    private static Ticket[] ticketsSold = new Ticket[48];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option = 0;
        while (option !=8){
            System.out.println(
                    "\n1)Buy a ticket" +
                            "\n2)Cancel ticket" +
                            "\n3)See seating plan" +
                            "\n4)Find first seat available" +
                            "\n5)Print tickets information and total price" +
                            "\n6)Search ticket" +
                            "\n7)Sort tickets by price" +
                            "\n8)Exit");

            System.out.println("Please select an option from the following: ");


            if(input.hasNextInt()){
                option = input.nextInt();
                switch (option){
                    case 1:
                        buy_ticket(input);
                        break;
                    case 2:
                        cancel_ticket(input);
                        break;
                    case 3:
                        print_seating_area();
                        break;
                    case 4:
                        find_first_available();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket(input);
                        break;
                    case 7:
                        sort_tickets();
                        break;
                    default:
                        System.out.println("Please enter a number between 1 and 8");
                        break;
                }
            }
            else{
                System.out.println("Invalid option, please re-enter");
                input.next();
            }
        }
    }
    public static void buy_ticket(Scanner input){
        while (true){
            int row;
            int seat;
            while(true){
                System.out.println("Enter a row number between (1-3): ");
                if(!input.hasNextInt()){
                    System.out.println("Invalid input please re-enter a valid input");
                    input.next(); //Clear invalid input
                    continue;
                }

                row = input.nextInt();  //Subtract 1 to use a zero based index
                input.nextLine();//Consume the newline character
                if(row < 1 || row > seatingPlan.length) {
                    System.out.println("Invalid row number");
                    continue;
                }
                break;
            }
            while(true){
                System.out.println("Enter a seat number between (1-16): ");
                if(!input.hasNextInt()){
                    System.out.println("Invalid input please re-enter a valid input");
                    input.next(); //Clear invalid input
                    continue;
                }
                seat = input.nextInt(); //Subtract 1 to get a zero based index;
                input.nextLine();

                if( seat < 1 || seat > seatingPlan[row-1].length){
                    System.out.println("Invalid seat number");
                    continue;
                }
                break;
            }
            String name = "";
            while(name.isEmpty()){
                System.out.println("Enter your name: ");
                name = input.nextLine();
                if(name.isEmpty()){
                    System.out.println("Name cannot be left blank, please re-enter");
                }
            }
            String surname = "";
            while(surname.isEmpty()){
                System.out.println("Enter your surname: ");
                surname = input.nextLine();
                if(surname.isEmpty()){
                    System.out.println("The surname cannot be left empty");
                }
            }
            String email = "";
            while(email.isEmpty()){
                System.out.println("Enter your email: ");
                email = input.nextLine();
                if(email.isEmpty()){
                    System.out.println("The email cannot be left empty");
                }
            }

            Person person = new Person(name, surname,email);
            Ticket ticket = new Ticket(row, seat, person);

            if(row == 1){
                ticket.setPrice(12);
            }
            else if(row == 2){
                ticket.setPrice(10);
            }
            else{
                ticket.setPrice(8);
            }

            if(seatingPlan[row-1][seat-1] == 1){
                System.out.println("This seat has already been booked");
                continue;
            }
            else{
                seatingPlan[row-1][seat-1] = 1;
                System.out.println("Your seat is booked successfully");
                addTicket(ticket);//Add ticket to the ticketsSold array
                break;
            }
        }
    }
    public static void addTicket(Ticket ticket){
        for(int i = 0; i < ticketsSold.length; i++ ){
            if (ticketsSold[i] == null){
                ticketsSold[i] = ticket;
                break;
            }
        }
    }



    public static void cancel_ticket(Scanner input){
        while (true){
            int row;
            int seat;
            while(true){
                System.out.println("Enter a row number between (1-3) that you want to cancel: ");
                if(!input.hasNextInt()){
                    System.out.println("Invalid input please re-enter a valid input");
                    input.next(); //Clear invalid input
                    continue;
                }

                row = input.nextInt();  //Subtract 1 to use a zero based index
                if(row < 1 || row > seatingPlan.length) {
                    System.out.println("Invalid row number");
                    continue;
                }
                break;
            }
            while(true){
                System.out.println("Enter a seat number between (1-16) that you want to cancel): ");
                if(!input.hasNextInt()){
                    System.out.println("Invalid input please re-enter a valid input");
                    input.next(); //Clear invalid input
                    continue;
                }
                seat = input.nextInt(); //Subtract 1 to get a zero based index;
                if( seat < 1 || seat > seatingPlan[row - 1].length){
                    System.out.println("Invalid seat number");
                    continue;
                }
                break;
            }
            if(seatingPlan[row-1][seat-1] == 0){
                System.out.println("This seat is already available");
                continue;
            }
            else{
                seatingPlan[row-1][seat-1] = 0;
                System.out.println("Your seat is cancelled successfully");
                removeTicket(row, seat);
                break;
            }
        }
    }

    public static void removeTicket(int row, int seat){
        for(int i = 0; i < ticketsSold.length; i++){
            if(ticketsSold[i] != null && ticketsSold[i].getRowNumber() == row && ticketsSold[i].getSeatNumber() == seat){
                ticketsSold[i] = null;
                break;
            }
        }
    }
    public static void print_seating_area() {
        System.out.println("Seating Plan");
        for(int row = 0; row < seatingPlan.length; row++){
            for(int seat = 0; seat < seatingPlan[row].length; seat++){
                if (seatingPlan[row][seat] == 0){
                    System.out.print("O ");
                }
                else{
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        System.out.println("Screen is at the top");
    }
    public static void find_first_available() {
        for(int row = 0; row < seatingPlan.length; row++){
            for(int seat = 0; seat < seatingPlan[row].length; seat++){
                if(seatingPlan[row][seat] == 0){
                    System.out.println("The first seat is available in row" +(row + 1) + " seat" + (seat + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats found");
    }
    public static void print_tickets_info() {
        int totalPrice = 0;
        for(Ticket ticket: ticketsSold){
            if(ticket != null){
                ticket.displayTicket();
                totalPrice += ticket.getPrice();
                System.out.println("row" + ticket.getRowNumber() + ",seat" + ticket.getSeatNumber() + ",price" + ticket.getPrice());
                System.out.println();
            }
        }
        System.out.println("Total ticket sales were " + totalPrice);
        return;
    }
    public static void search_ticket(Scanner input){
        int row;
        int seat;
        while (true) {
            System.out.println("Enter a row number between (1-3): ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid input please re-enter a valid input");
                input.next(); //Clear invalid input
                continue;
            }

            row = input.nextInt();  //Subtract 1 to use a zero based index
            input.nextLine();//Consume the newline character
            if (row < 1 || row > seatingPlan.length) {
                System.out.println("Invalid row number");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Enter a seat number between (1-16): ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid input please re-enter a valid input");
                input.next(); //Clear invalid input
                continue;
            }
            seat = input.nextInt(); //Subtract 1 to get a zero based index;
            input.nextLine();

            if (seat < 1 || seat > seatingPlan[row - 1].length) {
                System.out.println("Invalid seat number");
                continue;
            }
            break;
        }
        if(seatingPlan[row-1][seat-1] == 0){
            System.out.println("This seat is available");
        }
        else{
            for(Ticket ticket: ticketsSold){
                if(ticket!=null && ticket.getRowNumber() == row && ticket.getSeatNumber() == seat){
                    ticket.displayTicket();
                    return;
                }
            }
            System.out.println("Ticket not found");
        }

    }
    public static void sort_tickets() {
        int n = ticketsSold.length;
        for(int i = 0; i < n-1; i++){ //Outer loop: Iterate through each element except the last one
            int min_idx = i; //Assume that the current element is the minimum
            for(int j = i + 1; j < n; j++ ){ // Inner loop: find the minimum element in the unsorted part
                if (ticketsSold[j] != null && ticketsSold[min_idx] != null){
                    if (ticketsSold[j].getPrice() < ticketsSold[min_idx].getPrice()){ //Compare prices
                        min_idx = j; //Update min_idx if a smaller element is found
                    }
                }

            }
            //Swap the found minimum element with the first element of the unsorted part
            Ticket temp = ticketsSold[min_idx];
            ticketsSold[min_idx] = ticketsSold[i];
            ticketsSold[i] = temp;
        }
        //Print the sorted tickets
        System.out.println("Tickets sorted by price in ascending order:");
        for(Ticket ticket : ticketsSold){
            if ( ticket != null){
                ticket.displayTicket();
                System.out.println("Row " +ticket.getRowNumber() + " ,Seat " + ticket.getSeatNumber() + ", Price " + ticket.getPrice());
                System.out.println();
            }
        }
    }
}