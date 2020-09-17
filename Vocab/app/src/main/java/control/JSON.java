package control;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JSON {

    public static JSONArray vocab = new JSONArray();

    public ArrayList<String> toArrayList() {
        ArrayList<String> arr = new ArrayList<>();
        if (this.vocab != null) {
            for (int i = 0; i < this.vocab.length(); i ++) {
                try {
                    arr.add(this.vocab.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return arr;
    }
}
