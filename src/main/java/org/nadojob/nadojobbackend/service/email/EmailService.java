package org.nadojob.nadojobbackend.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nadojob.nadojobbackend.dto.email.EmailMessageDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(EmailMessageDto dto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(dto.getTo());
        simpleMailMessage.setSubject(dto.getSubject());
        simpleMailMessage.setText(dto.getBody());
        javaMailSender.send(simpleMailMessage);
    }

}
