import java.util.Arrays;

public class BattleshipGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int test[][] = new int[10][10];
		for (int[] i : test) {
			Arrays.fill(i, 0);
		}
		
		for (int i = 0; i < 10; ++i)
		{
			System.out.print("\n");
			for (int j = 0; j < 10; ++j) 
			{
				System.out.print(" " + j);
			}
		}
		
	}

}
