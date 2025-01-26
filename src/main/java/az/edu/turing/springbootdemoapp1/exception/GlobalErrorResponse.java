package az.edu.turing.springbootdemoapp1.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GlobalErrorResponse {

    private String errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;
    private UUID requestId;
}
