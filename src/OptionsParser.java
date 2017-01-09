import java.io.IOException;

class OptionsParser {

    public static void parse(String[] args) throws IOException {

        Parser parser = new Parser();
        if(args[0].equals("-u"))
        {
            parser.update();
        }
        else if (args[0].equals("-n"));
        else
        {
            System.out.println("Niepoprawne argumenty");
        }

        switch (args[1]) {
            case "1":
                if (parser.getID(args[2]) == 0) {
                    System.out.println("Nieprawidłowe imię i/lub nazwisko posła");
                } else
                    System.out.println(parser.getExpenses(parser.getID(args[2])));
                break;
            case "2" :
                if (parser.getID(args[2]) == 0) {
                    System.out.println("Nieprawidłowe imię i/lub nazwisko posła ");
                } else
                    System.out.println(parser.getSpecificExpences(args[3], parser.getID(args[2])));
                break;
            case "3":
                System.out.println(parser.averageValue());
                break;
            case "4":
                parser.getList(parser.mostTrips());
                break;
            case "5":
                parser.getList(parser.largestTrip());
                break;
            case "6":
                parser.getList(parser.mostExpensiveTrip());
                break;
            case "7":
                parser.getList(parser.visitorsItaly());
                break;
            default:
                System.out.println(
                    "Niepoprawne argumenty\nDostępne opcje : \n - u/n [numer polecenia] [wymagane argumenty]\n" +
                            "-u - aktualizacja\n"+
                            "-n - bez aktualizacji\n" +
                            "1 - suma wydatków posła/posłanki o określonym imieniu i nazwisku\n" +
                            "2 - wysokość wydatków o określonym tytule posła/posłanki \n" +
                            "3 - średnia wartość sumy wydatków wszystkich posłów\n" +
                            "4 - poseł/posłanka, który wykonał najwięcej podróży zagranicznych\n" +
                            "5 - poseł/posłanka, który najdłużej przebywał za granicą\n" +
                            "6 - poseł/posłanka, który odbył najdroższą podróż zagraniczną\n" +
                            "7 - lista wszystkich posłów, którzy odwiedzili Włochy");
                break;

        }

    }
}
