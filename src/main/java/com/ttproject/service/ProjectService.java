package com.ttproject.service;

import com.ttproject.dto.ProjectRequest;
import com.ttproject.dto.ProjectResponse;
import com.ttproject.entity.Project;
import com.ttproject.exception.InvalidDeadlineException;
import com.ttproject.exception.ProjectNotFoundException;
import com.ttproject.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public List<ProjectResponse> findAll() {
        return projectRepository.findAllByOrderByDeadlineAscIdAsc().stream()
                .map(ProjectResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse findById(Long id) {
        return ProjectResponse.from(getProject(id));
    }

    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        Project project = new Project();
        apply(request, project);
        return ProjectResponse.from(projectRepository.save(project));
    }

    @Transactional
    public ProjectResponse update(Long id, ProjectRequest request) {
        Project project = getProject(id);
        apply(request, project);
        return ProjectResponse.from(project);
    }

    @Transactional
    public void delete(Long id) {
        projectRepository.delete(getProject(id));
    }

    private Project getProject(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private void apply(ProjectRequest request, Project project) {
        if (request.deadline().isBefore(request.startDate())) {
            throw new InvalidDeadlineException();
        }
        project.setName(request.name().strip());
        project.setDescription(request.description().strip());
        project.setStartDate(request.startDate());
        project.setDeadline(request.deadline());
        project.setStatus(request.status());
    }
}
