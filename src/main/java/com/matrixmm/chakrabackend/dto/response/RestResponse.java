package com.matrixmm.chakrabackend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.matrixmm.chakrabackend.utils.Format;
import com.matrixmm.chakrabackend.utils.MyUtilMethods;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RestResponse {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Format.DATE_TIME)
    private LocalDateTime timestamp;
    private String message;
    @JsonInclude(Include.NON_NULL)
    private Object payload;

    public RestResponse() {
        this.timestamp = MyUtilMethods.getNowDateTime();
    }

    public RestResponse(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public RestResponse(HttpStatus status, String message, Object payload) {
        this(status, message);
        this.payload = payload;
    }
}