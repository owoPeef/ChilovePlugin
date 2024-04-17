package ru.peef.chilove.vk;

import org.bukkit.Bukkit;
import ru.peef.chilove.ChiloveMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Messages {
    static int VK_GROUP_ID = 219333163;
    static double VK_API_VERSION = 5.131;
    static String VK_API_KEY = "vk1.a._HU6NGwbaKD3TQGjOVIGtPJN5gcm7tDOCjPfqya6Z5K25BzNa8W3IYJALy5yk_wIjYGXhIsWkD1zH2zXI2AZvQudD5hi7wbmBy5up_hIyUnC6KplyGqJkVqj-DhUInRW4p3Y9MJjNZXqetd9zGbu9N8Bkd4ckyr5FA-X46QkPic4uaxM1YnKRQf4O_oLiIDqphsHMPU3u9uhTLfLmUjP7A";

    public static void send(int user_id, String message) {
        try {
            int max = 30000000;
            int min = 100000;
            int rand_id = (int) ((Math.random() * (max - min)) + min);
            String endpoint_url = "https://api.vk.com/method/messages.send?access_token="
                    + VK_API_KEY + "&v=" + VK_API_VERSION + "&user_id=" + user_id + "&message=" + message + "&random_id=" + rand_id;
            if (ChiloveMain.debugMode) Bukkit.getLogger().info("vk.send(" + endpoint_url + ")".replace(VK_API_KEY, ""));
            URL url = new URL(endpoint_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Bukkit.getLogger().info(response.toString());
        } catch (Exception exception) {
            Bukkit.getLogger().info("vk.send(error) => " + exception.getMessage());
        }
    }
}
