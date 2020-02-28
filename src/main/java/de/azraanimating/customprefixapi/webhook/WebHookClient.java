package de.azraanimating.customprefixapi.webhook;

import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class WebHookClient {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private String url;

    public WebHookClient(String url) {
        this.url = url;
    }

    public WebHookClient(TextChannel channel, String name) {
        channel.retrieveWebhooks().complete().forEach(webHook -> {
            if (webHook.getName().equals(name)) {
                this.url = webHook.getUrl();
            }
        });

        if (this.url == null) {
            this.url = channel.createWebhook(name).complete().getUrl();
        }
    }


    public String generateFileName(String folder) {
        StringBuilder builder = new StringBuilder();
        builder.append(folder);
        int length = 20;
        while (length-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        if (new File(folder + builder.toString()).exists()) {
            return generateFileName(folder);
        }
        new File(builder.toString()).mkdirs();
        return builder.toString();
    }

    public WebHookClient send(WebHookMessage webHookMessage) {
        if (webHookMessage.getAttachmentList().isEmpty()) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Content-Type", "application/json");

                con.setRequestMethod("POST");
                con.setDoOutput(true);

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = webHookMessage.getJson().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            entity.setCharset(StandardCharsets.UTF_8);
            entity.setContentType(ContentType.MULTIPART_FORM_DATA);
            entity.addTextBody("payload_json", webHookMessage.getJson());

            Collection<File> files = new ArrayList<>();

            webHookMessage.getAttachmentList().forEach(attachment -> {
                try {
                    File file = attachment.downloadToFile(generateFileName("tmp\\") + "\\" + attachment.getFileName()).get();
                    entity.addPart(attachment.getFileName(), new FileBody(file));
                    files.add(file);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });

            HttpPost request = new HttpPost(url);
            request.setEntity(entity.build());


            HttpClient client = HttpClientBuilder.create().build();

            try {
                client.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            files.forEach(file -> {
                file.delete();
                file.getParentFile().delete();
            });
        }

        return this;
    }

}