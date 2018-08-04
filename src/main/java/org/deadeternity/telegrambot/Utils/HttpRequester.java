package org.deadeternity.telegrambot.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class HttpRequester {
    public static String[] getPage(String page) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] result = new String[2];
        try {
            URL url = new URL(page);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11" +
                        " (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()
                    )
            );

            String inputLine;

            while((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            result[0] = URLDecoder.decode(connection.getURL().toString(), "UTF-8");
            result[1] = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
