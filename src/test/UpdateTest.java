import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class UpdateTest {

    @Test
    public void getJSON() throws Exception {
        Update update = new Update();
        JSONObject jsonObject = update.getJSON(new URL("https://api-v3.mojepanstwo.pl/dane/poslowie/460.json"));
        Assert.assertTrue("getJson - assertTrue",jsonObject.length() != 0 );
        Assert.assertTrue("getJson - assertTrue",jsonObject.getString("id").equals("460"));
    }

    @Test
    public void getListIds() throws Exception {
        Update update = new Update();
        List<Integer> ids = update.getListIds();
        Assert.assertTrue("getListIds - assertTrue", ids.size() == 744);
    }

    @Test
    public void getFiles() throws Exception {
        Update update = new Update();
        update.getFiles();
        JSONObject obj1 = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/" + 1))));
        JSONObject obj2 = new JSONObject(new String(Files.readAllBytes(Paths.get("Pliki/UpdateTest/" + 1))));
        String s1 = obj1.getJSONObject("data").getString("poslowie.nazwa");
        String s2 = obj2.getJSONObject("data").getString("poslowie.nazwa");
        Assert.assertTrue("getFiles - assertTrue", s1.equals(s2));

    }

    @Test
    public void getIds() throws Exception {
        Update update = new Update();
        List<Integer> tmp = new LinkedList<>();
        tmp.add(12);tmp.add(3);tmp.add(450);
        tmp.add(4);tmp.add(5);
        List<Integer> result = update.getIds(new File("Pliki/UpdateTest/List"));
        Assert.assertTrue("getIds - assertTrue", Arrays.equals(tmp.toArray(), result.toArray()));
    }
}