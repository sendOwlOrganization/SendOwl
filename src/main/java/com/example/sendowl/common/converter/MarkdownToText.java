package com.example.sendowl.common.converter;

import lombok.Data;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Data
public class MarkdownToText {

    private String plainText;

    public MarkdownToText(String markText){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markText);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String htmlText = renderer.render(document);

        String plainText = htmlText.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");

        this.plainText = plainText;
    }

    public String markDownToText (String markText) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markText);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String htmlText = renderer.render(document);

        String plainText = htmlText.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");

        return plainText;
    }
}
