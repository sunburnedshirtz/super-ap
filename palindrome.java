import java.util.Scanner;
import java.util.Random;

class palindrome {
	public static void main(String args[]) {
		int num;
		int palindrome = 0;
		boolean isPalindrome;
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter a number: ");
		num = sc.nextInt();

		if((num % 10 == 0 && num != 0) || num < 0) {
			isPalindrome = false;
		}
			
		else {
			int temp = num;
			while(num > 0) {
				palindrome = palindrome * 10 + ( num % 10 );
				num /= 10;
			}
			if(palindrome == temp) {
				isPalindrome = true;
			}
			else {
				isPalindrome = false;
			}
		}
		System.out.println("is palindrome: "+isPalindrome);
	}
}