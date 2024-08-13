public class Ticket {
    public int row;
    public int seat;
    public int price;
    public Person person;

    public Ticket(int rowNumber, int seatNumber, int price, Person person) {
        this.row = rowNumber;
        this.seat = seatNumber;
        this.price = price;
        this.person = person;
    }
    public void print() {
        System.out.println("The ticket information " +
                "\n" + "name: " + person.name +
                "\n" + "surname: " + person.surname +
                "\n" + "email: " + person.email +
                "\n" + "row: " + row +
                "\n" + "seat: " + seat +
                "\n" + "price: LKR " +price);
        System.out.println();
    }


}
