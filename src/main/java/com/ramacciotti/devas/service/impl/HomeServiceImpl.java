package com.ramacciotti.devas.service.impl;

import com.ramacciotti.devas.dto.UserDTO;
import com.ramacciotti.devas.enums.Level;
import com.ramacciotti.devas.enums.Objective;
import com.ramacciotti.devas.enums.Preference;
import com.ramacciotti.devas.model.User;
import com.ramacciotti.devas.repository.UserRepository;
import com.ramacciotti.devas.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ramacciotti.devas.utils.TranslatorUtils.toEnglish;

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
    public List<UserDTO> getUsers(String search) {
        log.info("Starting getUsers with search filter: '{}'", search);

        List<User> users;

        if(search == null || search.isBlank()) {
            log.info("No search filter found. Returning all users...");
            users = userRepository.findAll();
        } else {
            String translatedSearch = translateOrFallback(search);
            log.info("Translated search filter: '{}'", translatedSearch);

            users = userRepository.searchUsers(translatedSearch);
            log.info("Number of users found with translated search filter: {}", users == null ? 0 : users.size());

            if (users == null) {
                log.warn("No users found on database!");
                return Collections.emptyList();
            }
        }

        return users.stream()
                .map(user -> {
                    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                    mapEnumDetails(user, userDTO);
                    return userDTO;
                })
                .collect(Collectors.toList());

    }

    private void mapEnumDetails(User user, UserDTO userDTO) {
        if (userDTO.getJob() == null) {
            return;
        }

        if (userDTO.getJob().getLevel() != null) {
            userDTO.getJob().setLevel(user.getJob().getLevel().getDescription());
        }

        if (userDTO.getJob().getPreference() != null) {
            userDTO.getJob().setPreference(user.getJob().getPreference().getDescription());
        }

        if (userDTO.getJob().getObjective() != null) {
            userDTO.getJob().setObjective(user.getJob().getObjective().getDescription());
        }

    }

    private String translateOrFallback(String text) {
        try {
            String translated = toEnglish(text);
            log.info("Translation successful: '{}' -> '{}'", text, translated);
            return translated;
        } catch (IOException e) {
            log.error("Translation failed for '{}'. Using original text. Error: {}", text, e.getMessage());
            return text;
        }
    }


    @Override
    public ResponseEntity<byte[]> getUserPhoto(Long id) {
        log.info("Retrieving user photo for user with ID {}...", id);

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            log.warn("User with ID: {} not found!", id);
            return ResponseEntity.notFound().build();
        }

        byte[] photo = userOpt.get().getAbout().getPhoto();
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
