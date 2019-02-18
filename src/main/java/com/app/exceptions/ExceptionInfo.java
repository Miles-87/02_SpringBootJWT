package com.app.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionInfo {
    private ExceptionCode exceptionCode;
    private String exceptionDescription;
    private LocalDateTime exceptionDateTime;

    public ExceptionInfo(ExceptionCode exceptionCode, String exceptionDescription) {
        this.exceptionCode = exceptionCode;
        this.exceptionDescription = exceptionDescription;
        this.exceptionDateTime = LocalDateTime.now();
    }
}
