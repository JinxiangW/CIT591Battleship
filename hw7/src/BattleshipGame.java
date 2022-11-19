import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BattleshipGame {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Ocean ocean = new Ocean();
		ocean.placeAllShipsRandomly();
		while (!ocean.isGameOver())
		{
			prompt(ocean, sc);
		}
		ocean.print();
		System.out.println("All ships have been elimilated, congrats!");
	}
	
	public static void prompt(Ocean ocean, Scanner sc) {
//		ocean.printWithShips();
		ocean.print();
		try 
		{
			System.out.println("Enter row, column");
			int row = sc.nextInt(), column = sc.nextInt();
			ocean.shootAt(row, column);
		} catch (InputMismatchException e)
		{
			System.out.println("Please enter a valid number\n");
			sc.nextLine();
		}
		
	}

}
