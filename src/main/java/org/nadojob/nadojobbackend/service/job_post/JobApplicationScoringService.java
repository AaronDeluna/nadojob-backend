package org.nadojob.nadojobbackend.service.job_post;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateMatchingDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostMatchingDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationScoringService {

    private final static String AI_PROMPT_MESSAGE =
            "Проанализируй соответствие резюме кандидата требованиям вакансии. Верни только одну числовую оценку от 0.00 до 1.00, где 1.00 — полное соответствие, 0.00 — полное несоответствие. Никаких комментариев, пояснений или текста — только число.";
    private final static String USER_PROMPT = "Резюме кандидата: %s\nВакансия:%s";
    private final static String AI_MODEL = "gpt-3.5-turbo";
    private final OpenAiService aiService;

    public BigDecimal scoreCandidate(CandidateMatchingDto candidate, JobPostMatchingDto jobPost) {
        List<ChatMessage> messages = List.of(
                new ChatMessage("system", AI_PROMPT_MESSAGE),
                new ChatMessage("user", String.format(USER_PROMPT, candidate.toString(), jobPost.toString()))
        );

        ChatCompletionRequest request = buildChatRequest(messages);
        ChatCompletionResult result = aiService.createChatCompletion(request);
        String content = getContent(result);

        try {
            return new BigDecimal(content.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new RuntimeException("AI вернул некорректный результат: " + content);
        }
    }

    private ChatCompletionRequest buildChatRequest(List<ChatMessage> messages) {
        return ChatCompletionRequest.builder()
                .model(AI_MODEL)
                .messages(messages)
                .temperature(0.0)
                .maxTokens(10)
                .build();
    }

    private String getContent(ChatCompletionResult result) {
        return result.getChoices().get(0).getMessage().getContent();
    }

}
