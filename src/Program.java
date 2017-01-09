import java.io.IOException;

public class Program {

    public static void main(String [] args) throws IOException {

        long startTime = System.currentTimeMillis();
        //Parser.getFiles();
        OptionsParser.parse(args);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime/1000);
        //System.out.println(Parser.getID(7,"Adam Szejnfeld"));
    }

}
