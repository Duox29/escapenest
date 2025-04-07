package com.duox.escapenest.dto.response.valueObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 *
 * @param <T>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultMessage<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        private boolean success;
        private String message;
        private Integer code;
        private long timestamp = System.currentTimeMillis();
        private T result;
}
