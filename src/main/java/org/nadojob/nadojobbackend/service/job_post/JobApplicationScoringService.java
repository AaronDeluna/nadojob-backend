package org.nadojob.nadojobbackend.service.job_post;

import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateMatchingDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostMatchingDto;
import org.nadojob.nadojobbackend.service.ai.OpenAiIntegrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobApplicationScoringService {

    @Value("${ai.prompt}")
    private String aiPromptMessage;
    private final static String USER_PROMPT = "Резюме кандидата: %s\nВакансия:%s";
    private final OpenAiIntegrationService openAiIntegrationService;

    public BigDecimal scoreCandidate(CandidateMatchingDto candidate, JobPostMatchingDto jobPost) {
        List<ChatMessage> messages = List.of(
                new ChatMessage("system", aiPromptMessage),
                new ChatMessage("user", String.format(USER_PROMPT, candidate.toString(), jobPost.toString()))
        );

        String content = extractMessageText(openAiIntegrationService.sendMessage(messages));
        return parseScore(content);
    }

    private String extractMessageText(ChatCompletionResult result) {
        if (isInvalidChatCompletionResult(result)) {
            log.warn("Ошибка с возвратом ответа от AI: {}", result);
            throw new IllegalStateException("AI не вернул ни одного ответа");
        }
        return result.getChoices().get(0).getMessage().getContent();
    }

    private BigDecimal parseScore(String content) {
        String normalized = content.trim().replace(",", ".");
        if (!normalized.matches("^0(\\.\\d{1,2})?$|^1(\\.00?)?$")) {
            log.warn("AI вернул неожиданный формат: {}", content);
            throw new RuntimeException("AI вернул некорректный результат: " + content);
        }
        return new BigDecimal(normalized);
    }

    private boolean isInvalidChatCompletionResult(ChatCompletionResult result) {
        return result == null || result.getChoices() == null || result.getChoices().isEmpty();
    }

}
