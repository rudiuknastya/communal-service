package com.example.chairman.model.voting;

import com.example.chairman.entity.VotingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record VotingFormDto(
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
        String subject,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=400, message = "Розмір поля має бути не більше 400 символів")
        String text,
        @NotNull(message = "Поле не може бути порожнім")
        LocalDateTime startDate,
        @NotNull(message = "Поле не може бути порожнім")
        LocalDateTime endDate,
        @NotNull(message = "Поле не може бути порожнім")
        Long quorum,
        @NotNull(message = "Поле не може бути порожнім")
        VotingStatus status
) {
}
