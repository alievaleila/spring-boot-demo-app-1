package az.edu.turing.springbootdemoapp1.controller;

import az.edu.turing.springbootdemoapp1.exception.GlobalErrorResponse;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import az.edu.turing.springbootdemoapp1.model.constant.ErrorCode;
import az.edu.turing.springbootdemoapp1.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Set;

import static az.edu.turing.springbootdemoapp1.common.TestConstants.USERNAME;
import static az.edu.turing.springbootdemoapp1.common.TestConstants.USER_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void getAll_Should_ReturnSuccess() throws Exception {
        given(userService.findAll()).willReturn(Set.of(USER_DTO));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(USER_DTO))))
                .andDo(MockMvcResultHandlers.print());
        then(userService).should(times(1)).findAll();


    }

    @Test
    void getByUsername_Should_ReturnSuccess() throws Exception {
        given(userService.findByUsername(USERNAME)).willReturn(USER_DTO);

        mockMvc.perform(get("/api/v1/users/{username}", USERNAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)))
                .andDo(MockMvcResultHandlers.print());
        then(userService).should(times(1)).findByUsername(USERNAME);

    }

    @Test
    void getByUsername_Should_Return404_When_UsernameNotfound() throws Exception {
        GlobalErrorResponse globalErrorResponse = GlobalErrorResponse.builder()
                .requestId(any())
                .timestamp(any())
                .errorCode(ErrorCode.NOT_FOUND)
                .errorMessage("Username with username admin@turing.edu.az not found!")
                .build();

        given(userService.findByUsername(USERNAME)).willThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/users/{username}", USERNAME))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(globalErrorResponse)))
                .andDo(MockMvcResultHandlers.print());
        then(userService).should(times(1)).findByUsername(USERNAME);
    }
}
