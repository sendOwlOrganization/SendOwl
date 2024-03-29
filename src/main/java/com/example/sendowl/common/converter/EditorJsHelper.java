package com.example.sendowl.common.converter;

import com.example.sendowl.domain.board.dto.BoardDto;
import org.springframework.stereotype.Component;

@Component
public class EditorJsHelper {

    private String sanitize(String text) {
        // 여기에 html 블록만 제거하는 코드 추가
        String specialCharacters = "[^\uAC00-\uD7A30-9a-zA-Z]";
        String refinedText = text.replaceAll(specialCharacters, " ");

        return refinedText;
    }

    public String extractText(BoardDto.EditorJsContent item) {
        var builder = new StringBuilder();
        for (var block : item.getBlocks()) {
            if (block.getType().equals("list")) {
                for (String s : block.getData().getItems()) {
                    var sanitizedText = this.sanitize(s);
                    builder.append(sanitizedText + " ");
                }
            } else {
                var sanitizedText = this.sanitize(block.getData().getText());
                builder.append(sanitizedText + " ");
            }
        }

        // 글자수 제한 로직 추가
        if (builder.toString().length() > 100) {
            return builder.substring(0, 100);
        } else {
            return builder.toString();
        }

    }
}
