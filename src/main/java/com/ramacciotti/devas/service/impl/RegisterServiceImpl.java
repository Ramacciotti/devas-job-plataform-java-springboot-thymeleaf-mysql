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
        log.info("Registering user in the database...");

        Optional<User> databaseUser = userRepository.findByEmail(userDTO.getEmail());
        if (databaseUser.isPresent()) {
            log.error("This user already exists!");
            throw new IllegalArgumentException("this_email_is_already_registered");
        }

        final AboutDTO aboutDTO = userDTO.getAbout();
        final About about = new About();
        about.setName(aboutDTO.getName());
        about.setAge(aboutDTO.getAge());
        about.setCity(aboutDTO.getCity());
        about.setDescription(aboutDTO.getDescription());

        try {
            if (aboutDTO.getPhoto() != null && !aboutDTO.getPhoto().isEmpty()) {
                about.setPhoto(aboutDTO.getPhoto().getBytes());
                log.info("User photo set successfully.");
            }
        } catch (IOException e) {
            log.error("Failed to read photo bytes.", e);
            throw new RuntimeException("Failed to read photo bytes", e);
        }

        final Social social = modelMapper.map(userDTO.getSocial(), Social.class);
        final Job job = modelMapper.map(userDTO.getJob(), Job.class);

        final User newUser = new User()
                .withEmail(userDTO.getEmail())
                .withPassword(userDTO.getPassword())
                .withAbout(about)
                .withSocial(social)
                .withJob(job)
                .withTechnology(userDTO.getTechnology());

        log.info("New user: {}", newUser);

        final Status status = new Status();
        status.setLogged(false);
        newUser.setStatus(status);
        log.info("New user status: {}", status);

        userRepository.save(newUser);
        log.info("New user successfully saved in the database!");

    }

}