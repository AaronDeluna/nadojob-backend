package org.nadojob.nadojobbackend.service.ai;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.exception.OpenAiIntegrationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiIntegrationService {

    @Value("${ai.model}")
    private String aiModel;
    private final OpenAiService openAiService;

    public ChatCompletionResult sendMessage(List<ChatMessage> messages) {
        if (messages == null || messages.isEmpty()) {
            throw new IllegalArgumentException("Список сообщений не должен быть пустым");
        }
        try {
            ChatCompletionRequest request = buildChatRequest(messages);
            return openAiService.createChatCompletion(request);
        } catch (OpenAiHttpException e) {
            throw new OpenAiIntegrationException("Произошла ошибка при интеграции с AI", e);
        }
    }

    private ChatCompletionRequest buildChatRequest(List<ChatMessage> messages) {
        return ChatCompletionRequest.builder()
                .model(aiModel)
                .messages(messages)
                .temperature(0.0)
                .maxTokens(10)
                .build();
    }

}
