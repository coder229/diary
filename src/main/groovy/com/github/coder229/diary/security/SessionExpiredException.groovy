package com.github.coder229.diary.security

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by scott on 9/26/2015.
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Session expired")
class SessionExpiredException extends RuntimeException {
}
