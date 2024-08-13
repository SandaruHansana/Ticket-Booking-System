import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static final int maxSeatsPerRow = 20;
	public static int[] firstRow = new int[12];
	public static int[] secondRow = new int[16];
	public static int[] thirdRow = new int[20];
	public static ArrayList<Ticket> ticketArray = new ArrayList<>();
	public static int totalPrice = 0;
	static int option;
	static int price;

	public static void main(String[] args) {
		System.out.println();
		System.out.println("Welcome to the New Theatre");
		System.out.println("--------------------------------------------------");
		System.out.println("Please select an option:");
		System.out.println("1) Buy a ticket");
		System.out.println("2) Print seating area");
		System.out.println("3) cancel tickets");
		System.out.println("4) List available seats");
		System.out.println("5) Save to file");
		System.out.println("6) Load from file");
		System.out.println("7) Print ticket information and total price");
		System.out.println("8) Sort tickets by price");
		System.out.println("     0) Quit");
		System.out.println("--------------------------------------------------");
		//labelling the loop to break in case 0
		loop:
		//main will run until the code true
		while (true) {
			try {
				System.out.print("Enter Option: ");

				Scanner input = new Scanner(System.in);
				option = input.nextInt();
				System.out.println();

				switch (option) {
					case 0:
						break loop;
					case 1:
						buy_ticket();
						System.out.println();
						break;
					case 2:
						print_seating_area();
						System.out.println();
						break;
					case 3:
						cancel_ticket();
						System.out.println();
						break;
					case 4:
						show_available();
						System.out.println();
						break;
					case 5:
						save();
						System.out.println();
						break;
					case 6:
						load();
						System.out.println();
						break;
					case 7:
						show_tickets_info();
						System.out.println();
						break;
					case 8:
						sort_tickets();
						System.out.println();
						break;
				}
			} catch (Exception e) {
				System.out.println("Enter a valid number!");
				System.out.println();
			}
		}
	}

	public static void buy_ticket() { //
		try {
			//Get inputs of name, surname, email, row number and seat number
			Scanner input2 = new Scanner(System.in);
			System.out.print("Enter the Name: ");
			String name;
			name = input2.nextLine();
			System.out.print("Enter the Surname: ");
			String surname;
			surname = input2.nextLine();
			System.out.print("Enter the E-mail: ");
			String email;
			email = input2.nextLine();
			//Send details to person class
			Person person = new Person(name, surname, email);
			int rowNumber;
			int seatNumber;
			System.out.print("Enter the row number: ");
			rowNumber = input2.nextInt();
			System.out.print("Enter the seat number: ");
			seatNumber = input2.nextInt();

			//created seatAvailable variable and created switch case to check whether seats booked earlier or not
			boolean seatAvailable = false;

			switch (rowNumber) {
				case 1 -> seatAvailable = firstRow[seatNumber - 1] == 0;
				case 2 -> seatAvailable = secondRow[seatNumber - 1] == 0;
				case 3 -> seatAvailable = thirdRow[seatNumber - 1] == 0;
			}
			//if seatAvailable false then assigned prize according to row number from switch case
			if (seatAvailable) {
				price = switch (rowNumber) {
					case 1 -> 300;
					case 2 -> 200;
					case 3 -> 100;
					default -> 0;
				};
				//Send details to Ticket class through person object
				Ticket ticket = new Ticket(rowNumber, seatNumber, price, person);
				//change the value of array from switch case according to the user input information
				switch (rowNumber) {
					case 1 -> firstRow[seatNumber - 1] = 1;
					case 2 -> secondRow[seatNumber - 1] = 1;
					case 3 -> thirdRow[seatNumber - 1] = 1;
					default -> System.out.println("Enter Valid Integer!");
				}
				//add ticket class details to ticket array
				ticketArray.add(ticket);
				System.out.println("You bought ticket in row " + rowNumber + " seat " + seatNumber);
				//if seatAvailable true then print warning error and then load the main menu
			} else {
				System.out.println("This seat bought earlier! Try another seat.");
			}
		} catch (Exception e) {
			System.out.println("Enter Valid integer!");
		}
	}

	public static void print_seating_area() {
		System.out.println("     ***********");
		System.out.println("     *  Stage  *");
		System.out.println("     ***********");
		printRow(firstRow);
		printRow(secondRow);
		printRow(thirdRow);
	}

	private static void printRow(int[] seats) {
		if (seats.length < maxSeatsPerRow) {
			// Calculate the number of empty spaces to center the seats in the row
			int emptySpaces = maxSeatsPerRow - seats.length;
			int emptySpacePerSide = emptySpaces / 2;
			// Print empty spaces to center the seats in the row
			for (int i = 0; i < emptySpacePerSide; i++) {
				System.out.print(" ");
			}
		}
		// Print the seats in the row
		for (int i = 0; i < seats.length; i++) {
			// Add a space between the two halves of the row
			if (i == seats.length / 2) {
				System.out.print(" ");
			}
			// Print an 'X' for a reserved seat or a '0' for an available seat
			if (seats[i] == 1) {
				System.out.print("X");
			} else {
				System.out.print("0");
			}
		}
		System.out.println();
	}

	public static void cancel_ticket() {
		try {
			//Get inputs of the row number and seat number to cancel the ticket
			Scanner input2 = new Scanner(System.in);
			System.out.print("Enter the row number: ");
			int rowNumber;
			rowNumber = input2.nextInt();
			System.out.print("Enter the seat number: ");
			int seatNumber;
			seatNumber = input2.nextInt();
			Ticket matchedTicket = null;
			//Iterating through arraylist to find the right ticket to cancel
			for (Ticket ticket : ticketArray) {
				if (ticket.row == rowNumber && ticket.seat == seatNumber) {
					matchedTicket = ticket;
				}
			}
			//created seatAvailable variable and created switch case to check whether seat booked or not
			boolean seatAvailable = false;
			switch (rowNumber) {
				case 1 -> seatAvailable = firstRow[seatNumber - 1] == 1;
				case 2 -> seatAvailable = secondRow[seatNumber - 1] == 1;
				case 3 -> seatAvailable = thirdRow[seatNumber - 1] == 1;
			}
			// change the array value of that seat to 0 if seat not booked earlier
			if (seatAvailable) {
				switch (rowNumber) {
					case 1 -> firstRow[seatNumber - 1] = 0;
					case 2 -> secondRow[seatNumber - 1] = 0;
					case 3 -> thirdRow[seatNumber - 1] = 0;
					default -> System.out.println("Try Again");
				}
				//remove the details ticketArray array list and print the ticket cancelled message
				ticketArray.remove(matchedTicket);
				System.out.println("You cancelled ticket in row " + rowNumber + " seat " + seatNumber);
			}
			//if ticket available true then print the warning message and load the main menu
			else {
				System.out.println("This seat did not anyone bought before.Try again carefully!");
			}
		} catch (Exception e) {
			System.out.println("Enter Valid integer!");
		}
	}

	public static void show_available() {
		//Used seatRow method to print available seat rows one by one
		System.out.print("Seats available in Row 1: ");
		seatRow(firstRow);
		System.out.print("Seats available in Row 2: ");
		seatRow(secondRow);
		System.out.print("Seats available in Row 3: ");
		seatRow(thirdRow);
	}

	public static void seatRow(int[] seatRow) {
		//created variable as "i" then created for loop from i to seatRow last seat  to load until last
		int i;
		for (i = 0; i < seatRow.length; i++) {
			//Calculate and print seatRows location plus one(arrays start from 0) if seatRows array value is 0
			if (seatRow[i] == 0) {
				System.out.print((i + 1) + " ");
			}
		}
		System.out.println();
	}

	//reference: https://www.w3schools.com/java/java_files_create.asp
	public static void save() {
		try {
			//create new FileWriter as ticketInformation and create the file "Theatre.txt"
			FileWriter ticketInformation = new FileWriter("Theatre.txt");
			ticketInformation.write("Row 1: ");
			//Iterating through firstRow array to write the data of array
			for (int i : firstRow) {
				ticketInformation.write(i + " ");
			}
			ticketInformation.write("\n");
			ticketInformation.write("Row 2: ");
			//Iterating through secondRow array to write the data of array
			for (int i : secondRow) {
				ticketInformation.write(i + " ");
			}
			ticketInformation.write("\n");
			ticketInformation.write("Row 3: ");
			//Iterating through thirdRow array to write the data of array
			for (int i : thirdRow) {
				ticketInformation.write(i + " ");
			}
			ticketInformation.write("\n");
			//close the file and print "saved successfully" message
			ticketInformation.close();
			System.out.println("Saved successfully");
		} catch (IOException e) {
			System.out.println("Error! Can't save seats to Files.");
			e.printStackTrace();
		}
	}

	//reference: https://www.w3schools.com/java/java_files_read.asp
	public static void load() {
		try {
			//load the "theatre.txt" file and assign ticketInformation FileWriter to Scanner
			File ticketInformation = new File("Theatre.txt");
			Scanner input = new Scanner(ticketInformation);
			int i = 1;
			//split data from ": " top get numbers of rows and seats for
			while (input.hasNextLine()) {
				String data = input.nextLine();
				String rows = data.split(": ")[1];
				String[] rowSeats = rows.split(" ");
				//get the booked seats in the row to load from arrayList
				for (int x = 0; x < rowSeats.length; x++) {
					if (i == 1) {
						firstRow[x] = Integer.parseInt(rowSeats[x]);
					} else if (i == 2) {
						secondRow[x] = Integer.parseInt(rowSeats[x]);
					} else if (i == 3) {
						thirdRow[x] = Integer.parseInt(rowSeats[x]);
					}
				}
				//print the data and add 1 to i
				System.out.println(data);
				i++;
			}
			System.out.println("Load successfully");
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't load seats. File not found");
		}
	}

	public static void show_tickets_info() {
		// Initialize the total price to 0
		totalPrice = 0;
		// Iterate over all tickets in the ticket array and print their details
		for (Ticket ticket : ticketArray) {
			ticket.print();
			// Add the price of the ticket to the total price
			totalPrice += ticket.price;
		}
		// Print the total price of all tickets
		System.out.println("Total Price: LKR " + totalPrice);
	}

	public static void sort_tickets() {
		// covert list to array
		Ticket[] tickets = ticketArray.toArray(new Ticket[1]);
		// pass the array and sort
		Ticket[] sortedTickets = mergeSort(tickets, 0, tickets.length - 1);
		// print sorted array if ticket has data
		for (Ticket ticket : sortedTickets) {
			if (ticket != null) ticket.print();
		}
	}

	//mergeSort code copied from lecture note of week 8
	public static Ticket[] mergeSort(Ticket[] array, int start, int end) {
		Ticket[] sorted;
		if (start < end) {
			int mid = (start + end) / 2;
			Ticket[] array_left = mergeSort(array, start, mid);
			Ticket[] array_right = mergeSort(array, mid + 1, end);
			sorted = merge(array_left, array_right);
		} else {
			sorted = new Ticket[1];
			sorted[0] = array[start];
		}
		return sorted;
	}

	public static Ticket[] merge(Ticket[] array_1, Ticket[] array_2) {
		Ticket[] merged = new Ticket[array_1.length + array_2.length];
		int index1 = 0, index2 = 0, indexMerged = 0;
		while (index1 < array_1.length && index2 < array_2.length) {
			if (array_1[index1].price <= array_2[index2].price) {
				merged[indexMerged] = array_1[index1];
				index1++;
			} else {
				merged[indexMerged] = array_2[index2];
				index2++;
			}
			indexMerged++;
		}
		while (index1 < array_1.length) {
			merged[indexMerged] = array_1[index1];
			index1++;
			indexMerged++;
		}
		while (index2 < array_2.length) {
			merged[indexMerged] = array_2[index2];
			index2++;
			indexMerged++;
		}
		return merged;
	}
}