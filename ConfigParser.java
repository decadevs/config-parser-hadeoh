package com.usmanadio;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigParser {
    public static Map<String, String> env = new HashMap<>();

    public ConfigParser(String fileName) {
        ReadWithNIO(fileName);
    }

    public void ReadWithNIO(String fileName) {

        Path path = Paths.get(fileName);

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            boolean appSeen = false;
            boolean read = false;

            for (String line : lines) {
                if (line.equals("[application]")) {
                    appSeen = true;
                }

                String nameString = "";
                if (appSeen && !read) {
                    nameString += "application.";
                }

                if (appSeen && line.equals("")) {
                    appSeen = false;
                    read = true;
                }

                if (!line.equals("") && !(appSeen && read)) {
                    if (line.contains("=")) {
                        nameString += line.split("=")[0];
                        String value = line.split("=")[1];
                        env.put(nameString, value);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("exception: " + e.getMessage());
        }
    }

    public String get(String key) {
        return env.get(key);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String envFileName = null;
        if (args.length == 0){
            envFileName = "D:\\JavaProjects\\Task-Two\\src\\config.txt";
        } else {
            String environment = args[0];
            if (environment.equalsIgnoreCase("development")) {
                envFileName = "D:\\JavaProjects\\Task-Two\\src\\config.txt.dev";
            } else if (environment.equalsIgnoreCase("staging")) {
                envFileName = "D:\\JavaProjects\\Task-Two\\src\\config.txt.staging";
            } else if (environment.equalsIgnoreCase("production")) {
                envFileName = "D:\\JavaProjects\\Task-Two\\src\\config.txt";
            }
        }
        ConfigParser config = new ConfigParser(envFileName);
        System.out.println(config.get("application.name"));
    }
}
