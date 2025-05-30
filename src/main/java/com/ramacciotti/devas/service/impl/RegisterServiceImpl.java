package com.ramacciotti.devas.service.impl;

import com.ramacciotti.devas.dto.AboutDTO;
import com.ramacciotti.devas.dto.UserDTO;
import com.ramacciotti.devas.model.*;
import com.ramacciotti.devas.repository.UserRepository;
import com.ramacciotti.devas.service.RegisterService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public RegisterServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void saveUser(UserDTO userDTO) {
        log.info("Registering user with email: {}", userDTO.getEmail());

        validateUserEmail(userDTO.getEmail());

        About about = mapAboutDTOToEntity(userDTO.getAbout());
        Social social = modelMapper.map(userDTO.getSocial(), Social.class);
        Job job = modelMapper.map(userDTO.getJob(), Job.class);
        Status status = createDefaultStatus();

        User newUser = new User()
                .withEmail(userDTO.getEmail())
                .withPassword(userDTO.getPassword())
                .withAbout(about)
                .withSocial(social)
                .withStatus(status)
                .withJob(job)
                .withTechnology(userDTO.getTechnology());

        userRepository.save(newUser);
        log.info("New user successfully saved in the database: {}", newUser);
    }

    private void validateUserEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            log.warn("User with email {} already exists.", email);
            throw new IllegalArgumentException("Email is already registered.");
        }
    }

    private About mapAboutDTOToEntity(AboutDTO aboutDTO) {
        About about = new About();
        about.setName(aboutDTO.getName());
        about.setAge(aboutDTO.getAge());
        about.setCity(aboutDTO.getCity());
        about.setDescription(aboutDTO.getDescription());

        if (aboutDTO.getPhoto() != null && !aboutDTO.getPhoto().isEmpty()) {
            try {
                about.setPhoto(aboutDTO.getPhoto().getBytes());
                log.info("User photo set successfully for: {}", aboutDTO.getName());
            } catch (IOException e) {
                log.error("Failed to process photo for user: {}", aboutDTO.getName(), e);
                throw new RuntimeException("Failed to process photo", e);
            }
        }
        return about;
    }

    private Status createDefaultStatus() {
        Status status = new Status();
        status.setCreatedAt(LocalDateTime.now());
        log.info("Default status created at: {}", status.getCreatedAt());
        return status;
    }

}