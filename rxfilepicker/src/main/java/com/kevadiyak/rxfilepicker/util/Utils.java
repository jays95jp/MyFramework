package com.kevadiyak.rxfilepicker.util;

import com.android.internal.util.Predicate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The type Utils.
 */
public class Utils {

    /**
     * Filter collection.
     *
     * @param <T>       the type parameter
     * @param target    the target
     * @param predicate the predicate
     * @return the collection
     */
    public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    /**
     * Gets file extension.
     *
     * @param file the file
     * @return the file extension
     */
    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Contains boolean.
     *
     * @param types the types
     * @param path  the path
     * @return the boolean
     */
    public static boolean contains(String[] types, String path) {
        for (String string : types) {
            if (path.endsWith(string)) return true;
        }
        return false;
    }
}
