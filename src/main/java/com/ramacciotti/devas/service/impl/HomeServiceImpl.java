package com.ramacciotti.devas.service.impl;

import com.ramacciotti.devas.dto.UserDTO;
import com.ramacciotti.devas.model.User;
import com.ramacciotti.devas.repository.UserRepository;
import com.ramacciotti.devas.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ramacciotti.devas.utils.EnumTranslator.translateEnums;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public HomeServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> getUsers() {
        log.info("Retrieving complete list of users...");

        final List<User> users = userRepository.findAll();

        log.info("Mapping list of {} users to UserDTO...", users.size());
        final List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            translateEnums(user, userDTO);
            return userDTO;
        }).collect(Collectors.toList());

        log.info("UserDTO list successfully created with {} users!", userDTOs.size());
        return userDTOs;
    }

    @Override
    public ResponseEntity<byte[]> getUserPhoto(Long id) {
        log.info("Retrieving user photo for user with ID {}...", id);

        final Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            log.warn("User with ID: {} not found!", id);
            return ResponseEntity.notFound().build();
        }

        final byte[] photo = userOpt.get().getAbout().getPhoto();
        if (photo == null) {
            log.warn("User with ID: {} does not have a photo!", id);
            return ResponseEntity.notFound().build();
        }

        log.info("User photo successfully retrieved for user with ID: {}", id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photo);
    }

}