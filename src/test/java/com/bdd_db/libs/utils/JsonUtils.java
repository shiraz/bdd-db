package com.bdd_db.libs.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * <p>
 * A class containing all JSON utilities.
 * </p>
 */
public class JsonUtils {

    /**
     * <p>
     * A constructor to prevent instantiation.
     * </p>
     */
    private JsonUtils() {}

    /**
     * <p>
     * Converts a string to JSON.
     * </p>
     *
     * @param string {@link String} - The string to convert.
     * @return {@link JsonElement} - The resultant JSON.
     */
    public static JsonElement convertStringToJson(String string) {
        return new JsonParser().parse(string);
    }
}
