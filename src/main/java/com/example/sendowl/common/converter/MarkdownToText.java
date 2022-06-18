package com.example.sendowl.common.converter;

import lombok.Data;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Data
public class MarkdownToText {

    private String refinedText;

    public MarkdownToText(String markText){
        String specialCharacters = "[^\uAC00-\uD7A30-9a-zA-Z]";
        // String htmlSyntax = "<[^>]*>";

        String refinedText = markText.replaceAll(specialCharacters, " ");

        this.refinedText = refinedText;
    }

    public String markDownToText (String markText) {
        String specialCharacters = "[^\uAC00-\uD7A30-9a-zA-Z]";
        // String htmlSyntax = "<[^>]*>";

        String refinedText = markText.replaceAll(specialCharacters, " ");

        return refinedText;
    }
}
