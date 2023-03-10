package net.lelandcarter.lightweightBrowser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* Created By: Leland Carter
-- Last Modified 03/09/2023
*/

public final class ReadJSON {
    private static ReadJSON INSTANCE;

    private ReadJSON() {
    }

    public static ReadJSON getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ReadJSON();

        return INSTANCE;
    }

    public static String homeUrl;
    public static String searchUrl;

    public static List<Bookmarks> bookmarks = new ArrayList<>();

    public void getProfile() {
        InputStream input = ReadJSON.class.getResourceAsStream("Profile.json");

        if (input == null)
            throw new NullPointerException("Profile.json has been removed please reinstall");

        JSONTokener token = new JSONTokener(input);
        JSONObject object = new JSONObject(token);

        homeUrl = object.getString("home:url");
        searchUrl = object.getString("search:url");

        JSONArray array = object.getJSONArray("bookmarks");

        for (int i = 0; i < array.length(); i++) {
            bookmarks.add(new Bookmarks(array.getJSONObject(i).getString("name"),
                    array.getJSONObject(i).getString("url")));
        }
    }

    /*public void addBookmark(Bookmarks bookmark) {
        InputStream input = ReadJSON.class.getResourceAsStream("Profile.json");

        if (input == null)
            throw new NullPointerException("Profile.json has been removed please reinstall");

        JSONTokener token = new JSONTokener(input);
        JSONObject object = new JSONObject(token);

        JSONArray array = object.getJSONArray("bookmarks");

        JSONObject obj = new JSONObject();
        obj.put("name", bookmark.name);
        obj.put("url", bookmark.url);

        JSONArray arr = new JSONArray();
        arr.put(obj);

        array.put(obj);
    }*/
}
