public class Ticket {
    private int rowNumber;
    private int seatNumber;
    private double price;
    private Person person;

    public Ticket(int rowNumber, int seatNumber, Person person){
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.person = person;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void displayTicket(){
        System.out.println("row " + rowNumber);
        System.out.println("seat " + seatNumber);
        System.out.println("price " + price);
        System.out.println("Person information");
        person.displayPerson();
    }
}
