import java.util.Scanner;

public class PlayCard {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] left,right;
		String[] line;
		String nextline,outString;
		while(sc.hasNext()){
			nextline = sc.nextLine();
			
			if(nextline.contains("joker JOKER")){
				outString = "joker JOKER";
			}else{
				
				line = nextline.split("-");
				left = line[0].split(" ");
				right = line[1].split(" ");
				
				if(left.length == 4 && right.length !=4 ) {
					outString = line[0];
				}else if(left.length != 4 && right.length == 4 ){
					outString = line[1];
					
				}else if(left.length == right.length ){
					if(count(line[0]) > count(line[1])){
						outString = line[0];
					}else{
						outString = line[1];
					}
				}else{
					outString = "ERROR";
				}
			}
			System.out.println(outString);
		}
	}

	private static int count(String str) {
		return "345678910JQKA2jokerJOKER".indexOf(str);
	}
}
