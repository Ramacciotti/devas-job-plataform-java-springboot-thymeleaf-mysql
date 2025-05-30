package com.ramacciotti.devas.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslatorUtils {

    public static String toEnglish(String textoEmPortugues) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String encodedText = URLEncoder.encode(textoEmPortugues, StandardCharsets.UTF_8.toString());

        String url = String.format(
                "https://translate.googleapis.com/translate_a/single?client=gtx&sl=pt&tl=en&dt=t&q=%s",
                encodedText
        );

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro na resposta: " + response);
            }

            String resposta = response.body().string();
            // A resposta vem como um array aninhado, exemplo: [[[“Hello”,”Olá”,null,null,1]]]
            String translated = resposta.split("\"")[1];  // Pega o primeiro elemento traduzido

            return translated;
        }
    }

}

