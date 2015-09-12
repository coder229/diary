package com.github.coder229.diary.controller

import com.github.coder229.diary.dto.AuthRequest
import com.github.coder229.diary.dto.AuthResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Created by scott on 9/8/2015.
 */
@RestController
@RequestMapping(value = '${spring.data.rest.baseUri}/user')
class AuthController {

    @RequestMapping(value = '/login', method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    AuthResponse login(@RequestBody AuthRequest authRequest) {

    }
}
