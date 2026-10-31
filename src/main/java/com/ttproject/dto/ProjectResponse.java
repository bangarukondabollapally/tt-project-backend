package com.ttproject.dto;

import com.ttproject.entity.Project;
import com.ttproject.entity.ProjectStatus;
import java.time.LocalDate;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate deadline,
        ProjectStatus status) {

    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getDeadline(),
                project.getStatus());
    }
}
