package ubicomp.geodiary;

import androidx.room.TypeConverter;

import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static void fromString(String value) {
        /*
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
        */
    }

    @TypeConverter
    public static void fromArrayList(ArrayList<String> list) {
        //Gson gson = new Gson();
        //String json = gson.toJson(list);
        //return json;
    }
}
