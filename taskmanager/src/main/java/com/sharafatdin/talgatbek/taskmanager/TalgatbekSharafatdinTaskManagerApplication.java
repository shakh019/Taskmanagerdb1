package com.sharafatdin.talgatbek.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main Application Class
 * Task Management System
 * @author Talgatbek Sharafatdin
 */
@SpringBootApplication
@EnableAsync
public class TalgatbekSharafatdinTaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalgatbekSharafatdinTaskManagerApplication.class, args);
    }
}
