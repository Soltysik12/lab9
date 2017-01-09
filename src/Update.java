import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Update {

    public JSONObject getJSON(URL url) {
        JSONObject obj = new JSONObject();
        try {

            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            String line = "";
            while ((inputLine = in.readLine()) != null) {
                line += inputLine;
            }
            in.close();
            obj = new JSONObject(line);
        }
        catch(Exception e) {
            System.out.println("Problem z tworzeniem JSONObject");
        }
        return obj;
    }

    public List<Integer> getListIds () throws IOException {
        int id = 0;
        List<Integer> listIds = new ArrayList<Integer>();

        try {
            URL url = new URL("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
            JSONObject obj = getJSON(url);

            URL lastUrl = new URL(obj.getJSONObject("Links").getString("last"));
            URL selfUrl = url;

            while (!(lastUrl.toString().equals(selfUrl.toString()))) {

                obj = getJSON(url);

                for (Object jsonObject : obj.getJSONArray("Dataobject")) {
                    JSONObject json = (JSONObject) jsonObject;
                    id = Integer.parseInt(json.getString("id"));
                    listIds.add(id);
                }

                selfUrl = new URL(obj.getJSONObject("Links").getString("self"));
                URL nextUrl = selfUrl;
                if(obj.getJSONObject("Links").has("next")) {
                    nextUrl = new URL(obj.getJSONObject("Links").getString("next"));
                }
                url = nextUrl;
            }
        } catch (Exception e) {
            System.out.println("Błąd lub niepoprawny URL");
        }

        FileWriter writer = new FileWriter("Pliki/ListIds");
        for(Integer str : listIds) {
            writer.write(str + "" + System.lineSeparator());

        }
        writer.close();
        return listIds;
    }

    public void getFiles() throws IOException {

        List<Integer> ids =  getListIds();
        for(int id : ids)
        {
            File file = new File("Pliki/" + id);
            URL url = new URL("https://api-v3.mojepanstwo.pl/dane/poslowie/" + id + ".json?layers[]=wydatki&layers[]=wyjazdy");
            FileUtils.copyURLToFile(url, file);
        }
    }

    public List<Integer> getIds(File file) {
        List<Integer> listIds = new LinkedList<>();
        try {
            for(String ids : FileUtils.readLines(file,"UTF-8"))
            {
                listIds.add(Integer.parseInt(ids));
            }
        } catch (IOException e) {
            System.out.println("Błąd programu, spróbuj ponownie");
        }
        return listIds;
    }
}
