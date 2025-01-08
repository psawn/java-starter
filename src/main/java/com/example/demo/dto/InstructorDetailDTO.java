package com.example.demo.dto;

import lombok.Data;

@Data
public class InstructorDetailDTO {
    private int id;

    private String youtubeChannel;

    private String hobby;

    private InstructorDTO instructor;
}
