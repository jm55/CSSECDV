import java.util.Scanner;
import java.util.regex.Pattern;

class register{
	
	private final String usernameRegex = "[a-z0-9_\\-.]+";
    private final String passwordRegex = "[A-Za-z0-9~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:\\<,>.?/]+";
    private final int minLength = 8;
	
	public static void main(String[] args){
		new register();
	}
	
	public register(){
		Scanner sc = new Scanner(System.in);
		
		int size = Integer.parseInt(sc.nextLine());
		System.out.println(size);
		
		for(int i = 0; i < size; i++){
			String[] entry = sc.nextLine().split(" ");
			if(isValidPassword(entry[0])){
				System.out.println(entry[0] + " (" + entry[0].length() + ")" + " = " + entry[1] + " PASS");
			}else{
				System.out.println(entry[0] + " (" + entry[0].length() + ")" + " = " + entry[1] + " FAILED");
			}
		}
		
		sc.close();
	}
	
	private boolean isValidPassword(String password){
        final String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lowercase = uppercase.toLowerCase();
        final String digits = "0123456789";
        final String special = "~`!@#$%^&*()_-+={[}]|:\\<,>.?/";
        
        boolean uppercaseRegex = false;
        boolean lowercaseRegex = false;
        boolean digitRegex = false;
        boolean specialRegex = false;
        
        for(int u = 0; u < uppercase.length(); u++){
            if(password.contains(uppercase.charAt(u) + ""))
                uppercaseRegex = true;
            if(password.contains(lowercase.charAt(u) + ""))
                lowercaseRegex = true;
        }
        for(int d = 0; d < digits.length(); d++)
            if(password.contains(digits.charAt(d) + ""))
                digitRegex = true;
        for(int s = 0; s < special.length(); s++)
            if(password.contains(special.charAt(s) + ""))
                specialRegex = true;
            
        return (uppercaseRegex && lowercaseRegex && digitRegex && specialRegex) && (password.length() >= minLength);
    }
	
	private boolean isFieldInvalid(String username, String password){
        boolean usernameValid = Pattern.compile(usernameRegex).matcher(username).matches();
        boolean passwordValid = Pattern.compile(passwordRegex).matcher(password).matches();
        boolean confPasswordValid = Pattern.compile(passwordRegex).matcher(password).matches();
        if (usernameValid && passwordValid && confPasswordValid)
            return false;
        return true;
    }
}