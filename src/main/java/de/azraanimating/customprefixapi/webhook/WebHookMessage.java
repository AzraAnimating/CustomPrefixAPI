package de.azraanimating.customprefixapi.webhook;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebHookMessage {

    private JSONObject jsonObject;
    private List<Message.Attachment> attachmentList;

    public WebHookMessage(MessageEmbed messageEmbed) {
        attachmentList = new ArrayList<>();
        jsonObject = new JSONObject();

        JSONObject embed = new JSONObject();

        if (messageEmbed.getColor() != null)
            embed.put("color", parseHex("#" + Integer.toHexString(messageEmbed.getColor().getRGB()).substring(2)));
        if (messageEmbed.getUrl() != null) embed.put("url", messageEmbed.getUrl());
        if (messageEmbed.getTitle() != null) embed.put("title", messageEmbed.getTitle());
        if (messageEmbed.getDescription() != null) embed.put("description", messageEmbed.getDescription());
        if (messageEmbed.getFooter() != null)
            embed.put("footer", new JSONObject().put("text", messageEmbed.getFooter().getText()).put("icon_url", messageEmbed.getFooter().getIconUrl()));
        if (messageEmbed.getAuthor() != null)
            embed.put("author", new JSONObject().put("name", messageEmbed.getAuthor().getName()).put("icon_url", messageEmbed.getAuthor().getIconUrl()).put("url", messageEmbed.getAuthor().getUrl()));
        if (messageEmbed.getImage() != null)
            embed.put("image", new JSONObject().put("url", messageEmbed.getImage().getUrl()));
        if (messageEmbed.getThumbnail() != null)
            embed.put("thumbnail", new JSONObject().put("url", messageEmbed.getThumbnail().getUrl()));
        if (messageEmbed.getTimestamp() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            sdf.setTimeZone(TimeZone.getTimeZone("CET"));
            String timeStamp = sdf.format(Date.from(messageEmbed.getTimestamp().toInstant()));
            embed.put("timestamp", timeStamp);
        }

        JSONArray fields = new JSONArray();

        messageEmbed.getFields().forEach(field -> {
            fields.put(new JSONObject().put("name", field.getName()).put("value", field.getValue()).put("inline", field.isInline()));
        });

        if (!fields.isEmpty()) embed.put("fields", fields);

        jsonObject.put("embeds", new JSONArray().put(embed));

    }

    public WebHookMessage(Message message) {
        attachmentList = new ArrayList<>();
        jsonObject = new JSONObject();

        if (message.getContentDisplay() != null) jsonObject.put("content", message.getContentDisplay());

        attachmentList.addAll(message.getAttachments());

        JSONArray embeds = new JSONArray();
        message.getEmbeds().forEach(messageEmbed -> {

            JSONObject embed = new JSONObject();

            if (messageEmbed.getColor() != null)
                embed.put("color", parseHex("#" + Integer.toHexString(messageEmbed.getColor().getRGB()).substring(2)));
            if (messageEmbed.getUrl() != null) embed.put("url", messageEmbed.getUrl());
            if (messageEmbed.getTitle() != null) embed.put("title", messageEmbed.getTitle());
            if (messageEmbed.getDescription() != null) embed.put("description", messageEmbed.getDescription());
            if (messageEmbed.getFooter() != null)
                embed.put("footer", new JSONObject().put("text", messageEmbed.getFooter().getText()).put("icon_url", messageEmbed.getFooter().getIconUrl()));
            if (messageEmbed.getAuthor() != null)
                embed.put("author", new JSONObject().put("name", messageEmbed.getAuthor().getName()).put("icon_url", messageEmbed.getAuthor().getIconUrl()).put("url", messageEmbed.getAuthor().getUrl()));
            if (messageEmbed.getImage() != null)
                embed.put("image", new JSONObject().put("url", messageEmbed.getImage().getUrl()));
            if (messageEmbed.getThumbnail() != null)
                embed.put("thumbnail", new JSONObject().put("url", messageEmbed.getThumbnail().getUrl()));
            if (messageEmbed.getTimestamp() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                sdf.setTimeZone(TimeZone.getTimeZone("CET"));
                String timeStamp = sdf.format(Date.from(messageEmbed.getTimestamp().toInstant()));
                embed.put("timestamp", timeStamp);
            }

            JSONArray fields = new JSONArray();

            messageEmbed.getFields().forEach(field -> fields.put(new JSONObject().put("name", field.getName()).put("value", field.getValue()).put("inline", field.isInline())));

            if (!fields.isEmpty()) embed.put("fields", fields);

            embeds.put(embed);
        });

        if (!embeds.isEmpty()) {
            jsonObject.put("embeds", embeds);
        }
    }

    public WebHookMessage(String content) {
        attachmentList = new ArrayList<>();
        jsonObject = new JSONObject();

        jsonObject.put("content", content);
    }

    public String getJson() {
        return jsonObject.toString();
    }

    public WebHookMessage setName(String name) {
        jsonObject.put("username", name);
        return this;
    }

    public List<Message.Attachment> getAttachmentList() {
        return attachmentList;
    }

    public WebHookMessage setAvatar(String url) {
        jsonObject.put("avatar_url", url);
        return this;
    }

    private int parseHex(final String color) {
        final Matcher mx = Pattern.compile("^#([0-9a-z]{6})$", Pattern.CASE_INSENSITIVE).matcher(color);
        if (!mx.find())
            throw new IllegalArgumentException("invalid color value");
        return Integer.parseInt(mx.group(1), 16);
    }

}