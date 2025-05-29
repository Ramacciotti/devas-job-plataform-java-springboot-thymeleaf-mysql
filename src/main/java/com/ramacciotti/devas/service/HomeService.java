package com.ramacciotti.devas.service;

import com.ramacciotti.devas.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HomeService {

    List<UserDTO> getUsers();

    ResponseEntity<byte[]> getUserPhoto(Long id);

}
