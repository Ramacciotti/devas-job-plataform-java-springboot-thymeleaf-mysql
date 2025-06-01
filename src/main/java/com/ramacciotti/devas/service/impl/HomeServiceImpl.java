package com.ramacciotti.devas.service.impl;

import com.ramacciotti.devas.dto.UserDTO;
import com.ramacciotti.devas.enums.Level;
import com.ramacciotti.devas.enums.Objective;
import com.ramacciotti.devas.enums.Preference;
import com.ramacciotti.devas.model.User;
import com.ramacciotti.devas.repository.UserRepository;
import com.ramacciotti.devas.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
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
            users = searchUserWithFilter(search);

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

    private List<User> searchUserWithFilter(String search) {
        String normalizedLevel = normalizeLevelSearchBeforeTranslation(search);
        log.info("Normalized level before translation: '{}'", normalizedLevel);

        if (normalizedLevel != null) {
            log.info("Found normalized level in manual map: '{}'. Querying by job level...", normalizedLevel);
            List<User> users = userRepository.findByJobLevel(normalizedLevel);
            log.info("Users found by job level '{}': {}", normalizedLevel, users == null ? 0 : users.size());
            return users;
        }

        String translatedSearch = translateOrFallback(search);
        log.info("Translated search filter: '{}'", translatedSearch);

        List<User> users;

        if (isLevelSearch(translatedSearch)) {
            users = userRepository.findByJobLevel(translatedSearch);
        } else {
            users = userRepository.searchUsers(translatedSearch);
        }

        log.info("Number of users found with translated search filter: {}", users == null ? 0 : users.size());
        return users;
    }

    private String normalizeLevelSearchBeforeTranslation(String search) {
        String lower = search.toLowerCase();

        Map<String, String> manualMap = Map.of(
                "estagiária", "internship",
                "estágio", "internship",
                "pleno", "pleno",
                "júnior", "junior",
                "sênior", "senior",
                "especialista", "specialist"
        );

        return manualMap.getOrDefault(lower, null);
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

            translated = fixTranslationExceptions(translated);

            return formatTextForSearch(translated);
        } catch (IOException e) {
            log.error("Translation failed for '{}'. Using original text. Error: {}", text, e.getMessage());
            return formatTextForSearch(text);
        }
    }

    private String fixTranslationExceptions(String translated) {
        Map<String, String> corrections = Map.of(
                "full", "pleno",
                "trainee", "trainee",
                "internship", "internship"
        );

        String lower = translated.toLowerCase();

        if (corrections.containsKey(lower)) {
            return corrections.get(lower);
        }

        return translated;
    }

    private boolean isLevelSearch(String search) {
        return Set.of("internship","trainee", "junior", "full", "pleno", "senior", "specialist").contains(search.toLowerCase());
    }

    private String formatTextForSearch(String text) {
        return text.trim().toLowerCase().replaceAll("\\s+", "_");
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
