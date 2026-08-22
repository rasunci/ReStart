package com.ryanasuncion;

import com.ryanasuncion.review.JsonAggregation;

import java.io.InputStream;

public class App {

    private static String usersFile = "users.json";

    private static String[] fileNames = new String[]{
            "transactions_page_1.json",
            "transactions_page_2.json",
            "transactions_page_3.json"
    };

    public static void main(String[] args) {
        JsonAggregation jsonAggregation = null;

        try (InputStream inputStream = App.class.getClassLoader().getResourceAsStream(usersFile)) {
            jsonAggregation = new JsonAggregation(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jsonAggregation == null)
            return;

        for (String fileName : fileNames) {
            try (InputStream inputStream = App.class.getClassLoader().getResourceAsStream(fileName)) {
                if (inputStream == null) {
                    throw new IllegalArgumentException("File not found!");
                }
                jsonAggregation.process(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(jsonAggregation.generateReport());
    }
}
