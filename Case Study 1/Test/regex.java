import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex {
    public static void main(String[] args) {
        final String regex = "^[a-z0-9~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:;\"'<,>.?/]$";
        final String string = "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/abcdef";
        
        final Pattern pattern = Pattern.compile(regex);
		System.out.println(pattern.matcher(string).matches());
    }
}
