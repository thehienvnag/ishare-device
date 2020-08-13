/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.netutils;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author thehien
 */
public class HttpRequestWrapper {

    public static String getMethod(String link) throws IOException {
        return Request.Get(link)
                .setHeader("Accept-Charset", "utf-8")
                .execute()
                .returnContent()
                .asString();
    }
    public static String postMethod(String link, Form form) throws IOException {
        return Request.Post(link)
                .bodyForm(form.build())
                .execute()
                .returnContent()
                .asString();
    }

    public static HttpResponse postMethodEntity(String url, StringEntity entity) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.setEntity(entity);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(request);
        return response;
    }

}
