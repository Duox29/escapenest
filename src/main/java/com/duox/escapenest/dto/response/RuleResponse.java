package com.duox.escapenest.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RuleResponse {
    String rule_id;
    String ruleDescription;
    boolean major;
    boolean active;
}
