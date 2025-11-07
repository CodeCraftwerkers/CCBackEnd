package com.codecrafters.ccbackend.seeder;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.codecrafters.ccbackend.entity.User;
import com.codecrafters.ccbackend.repository.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bcrypt;

    public DatabaseSeeder(UserRepository userRepository, BCryptPasswordEncoder bcrypt){
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            User user_one = User.builder()
            .username("Daniella")
            .password(bcrypt.encode("1234567"))
            .email("daniella@email.com")
            .role("USER")
            .build();

            User user_two = User.builder()
            .username("doe")
            .password(bcrypt.encode("654321"))
            .email("doe@email.com")
            .role("USER")
            .build();

            User user_three = User.builder()
            .username("maria")
            .password(bcrypt.encode("654321"))
            .email("maria@email.com")
            .role("USER")
            .build();

           
            userRepository.saveAll(List.of(user_one, user_two, user_three));
        }
    }

}