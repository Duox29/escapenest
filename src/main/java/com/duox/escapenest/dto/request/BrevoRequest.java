package com.duox.escapenest.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrevoRequest {
    Sender sender;
    List<Recipient> to;
    String subject;
    String htmlContent;
}
