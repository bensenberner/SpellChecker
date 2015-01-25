import java.util.ArrayList;


public class SpellSuggest {
	public static void main(String[] args) {
		ArrayList<String> gutArrList = new ArrayList<>();
		String[] alphabetPlus = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
				"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "'"};
		String gut = "abcdef";
		//		String[] gutArr = gut.split("");
		//		for (int i = 1; i < gutArr.length; ++i) {
		//			gutArrList.add(gutArr[i]);
		//		}


		//addChar
		//		for (int i = 0; i <= gut.length(); ++i) {
		//			for (char letter = 'a'; letter <= 'z'; ++letter) {
		//				System.out.println(gut.substring(0, i) + letter + gut.substring(i, gut.length()));
		//			}
		//		}

		//removeChar

		//		for (int i = 0; i < gut.length(); ++i) {
		//			System.out.println(gut.substring(0, i) + gut.substring((i + 1), gut.length()));
		//		}

		//switchVal
//		for (int i = 0; i <= gut.length(); ++i) {
//			if (i + 2 < gut.length()) {
//				System.out.println(gut.substring(0, i) + gut.substring(i+1, i+2) + gut.substring(i, i+1) + gut.substring(i+2, gut.length()));
//			}
//			else if (i == gut.length() - 1) {
//				System.out.println(gut.substring(0, i - 1) + gut.substring(i, i + 1) + gut.substring(i - 1, i));
//			}
//		}
	}

	//	public String addChar(String guts) {
	//		
	//	}
}
/*
G O S H
A G O S H
print
G O S H
B G O S H
print
...
G O S H
G A O S H
print
G B O S H
print
...
G O S H
G O S H Y
print
G O S H Z
print
char letter = 'a', letter <= 'z'; letter ++


 */