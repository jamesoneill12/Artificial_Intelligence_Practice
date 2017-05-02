package Parsers;

import java.util.HashMap;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections.*;

/**
 * Created by 1 on 01/05/2017.
 */
public class FileParser {

    public List<String> getDocument(String filepath) {

        String root = "C:/Users/1/James/grctc/GRCTC_Project/Classification/Data/recent_uk_docs/2000/";
        String filePath = root + "asp-2000-6-enacted-data.txt";
        HashMap<String, String> map = new HashMap<String, String>();
        List<String> list = new ArrayList<>();

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

            list = stream
                .map(String::toLowerCase)
                .collect(Collectors.toList());

            //stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.forEach(System.out::println);
        return(list);
    }
}
