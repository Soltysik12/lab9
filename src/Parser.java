import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private List<Integer> listIds;

    public Parser() {
        Update update = new Update();
        this.listIds = update.getIds(new File("Pliki/ListIds"));
    }

    public Parser(List ids)
    {
        this.listIds = ids;
    }

    public void update() throws IOException {
        Update update = new Update();
        update.getFiles();
        this.listIds = update.getIds(new File("Pliki/ListIds"));
    }

    public int getID(String name) throws IOException {

        int id = 0;
        JSONObject obj;
        for (int ids : listIds) {
            obj = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + ids))));
            if (obj.getJSONObject("data").getString("poslowie.nazwa").equals(name)) {
                id = ids;
                break;
            }
        }
        return id;
    }

    public double getExpenses(int id) {

        double expences =  0;
        try {
            JSONObject obj = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + id))));
            JSONArray roczniki = obj.getJSONObject("layers").getJSONObject("wydatki").getJSONArray("roczniki");

            for (Object object : roczniki) {
                JSONArray rocznik = ((JSONObject) object).getJSONArray("pola");
                for (Object pole : rocznik) {
                    expences += Double.parseDouble((String) pole);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Błąd programu, spróbuj ponownie");
        }

        return Math.round(expences*100.0)/100.0;
    }

    public double getSpecificExpences(String tytul, int id) {
        double expences = 0.0;
        try {

            JSONObject obj = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + id))));
            JSONArray roczniki = obj.getJSONObject("layers").getJSONObject("wydatki").getJSONArray("roczniki");
            int numberExpences = -1;

            for(Object object : obj.getJSONObject("layers").getJSONObject("wydatki").getJSONArray("punkty"))
            {
                if(tytul.equals(((JSONObject) object).getString("tytul"))) {
                    numberExpences = Integer.parseInt(((JSONObject) object).getString("numer")) - 1;
                    break;
                }
            }
            if(numberExpences != -1) {
                for (Object object : roczniki) {
                    JSONArray rocznik = ((JSONObject) object).getJSONArray("pola");
                    expences += Double.parseDouble(rocznik.getString(numberExpences));
                }
            }
            else {
                System.out.println("Nieprawidłowy tytuł wydatków");            }
        }
       catch (IOException e) {
            System.out.println("Błąd programu, spróbuj ponownie");
        }
        return Math.round(expences*100.0)/100.0;
    }

    public double averageValue(){

        double sum = 0;
        JSONObject obj;


        try{
            for (int id : listIds){
                obj = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + id))));
                sum += getExpenses(id);
            }
        }
        catch (Exception e) {
            System.out.println("Błąd programu, spróbuj ponownie");
            return 0;
            }
        return Math.round(sum/listIds.size() * 100.0/100.0);
    }

    public List<String> mostTrips() {
        List<String> result = new LinkedList<>();
        JSONObject obj;
        int liczbaWycieczek = 0;
        int maxLiczbaWycieczek = 0;

        try {
            for(int id : listIds) {
                obj = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + id))));
                liczbaWycieczek = obj.getJSONObject("data").getInt("poslowie.liczba_wyjazdow");
                if(liczbaWycieczek > maxLiczbaWycieczek)
                {
                    result.clear();
                    result.add(obj.getJSONObject("data").getString("ludzie.nazwa"));
                    maxLiczbaWycieczek = liczbaWycieczek;
                }
                else if(liczbaWycieczek == maxLiczbaWycieczek)
                {
                    result.add(obj.getJSONObject("data").getString("ludzie.nazwa"));
                }
            }
        }
         catch (IOException e) {
             System.out.println("Błąd programu, spróbuj ponownie");        }

        return result;
    }

    public List<String> largestTrip() {
        List<String> result = new LinkedList<>();
        JSONObject obj;
        int liczbaDni = 0;
        int maxLiczbaDni = 0;

        try {

            for(int id : listIds) {
                liczbaDni = 0;
                obj = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + id))));
                String data = obj.getJSONObject("layers").get("wyjazdy").toString();
                Object json = new JSONTokener(data).nextValue();
                if (json instanceof JSONArray) {
                    JSONArray wyjazdy = obj.getJSONObject("layers").getJSONArray("wyjazdy");
                    for(Object object : wyjazdy)
                    {
                        liczbaDni += Integer.parseInt(((JSONObject) object).getString("liczba_dni"));
                    }
                    if(liczbaDni > maxLiczbaDni)
                    {
                        result.clear();
                        result.add(obj.getJSONObject("data").getString("ludzie.nazwa"));
                        maxLiczbaDni = liczbaDni;
                    }
                    else if(liczbaDni == maxLiczbaDni) {
                        result.add(obj.getJSONObject("data").getString("ludzie.nazwa"));
                    }
                }
            }

        }
        catch (IOException e) {
            System.out.println("Błąd programu, spróbuj ponownie");
        }
        return result;
    }

    public List<String> mostExpensiveTrip() {
        List<String> result = new LinkedList<>();

        JSONObject obj;
        float suma = 0;
        float maxSuma = 0;

        try {
            for(int ids : listIds) {
                suma = 0;
                obj = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + ids))));
                String data = obj.getJSONObject("layers").get("wyjazdy").toString();
                Object json = new JSONTokener(data).nextValue();
                if (json instanceof JSONArray) {
                    JSONArray wyjazdy = obj.getJSONObject("layers").getJSONArray("wyjazdy");
                    for(Object object : wyjazdy)
                    {
                        suma = Float.parseFloat(((JSONObject) object).getString("koszt_suma"));
                        if(suma > maxSuma)
                        {
                            result.clear();
                            result.add(obj.getJSONObject("data").getString("ludzie.nazwa"));
                            maxSuma = suma;

                        }
                        else if(suma == maxSuma)
                        {
                            result.add(obj.getJSONObject("data").getString("ludzie.nazwa"));
                        }
                    }
                }
            }

        }
         catch (IOException e) {
             System.out.println("Błąd programu, spróbuj ponownie");        }
        return result;
    }

    public List<String> visitorsItaly() {

        JSONObject obj;
        List<String> visitors = new LinkedList<>();
        String italy = "IT";

        try {
            for(int id : listIds) {

                obj = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + id))));

                String data = obj.getJSONObject("layers").get("wyjazdy").toString();
                Object json = new JSONTokener(data).nextValue();
                if (json instanceof JSONArray) {
                    JSONArray wyjazdy = obj.getJSONObject("layers").getJSONArray("wyjazdy");
                    for(Object object : wyjazdy) {
                        if(italy.equals(((JSONObject) object).getString("country_code"))) {
                            visitors.add(obj.getJSONObject("data").getString("ludzie.nazwa"));
                            break;
                        }
                    }
                }
            }
        }

        catch (IOException e) {
            System.out.println("Błąd programu, spróbuj ponownie");
        }
        return visitors;
    }

    public void getList(List<String> list) {
        for(int i = 0; i < list.size() ; i ++)
            System.out.println(list.get(i).toString());
    }
}


