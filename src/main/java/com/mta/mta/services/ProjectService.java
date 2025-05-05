package com.mta.mta.services;
import com.mta.mta.entity.Project;
import com.mta.mta.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService   {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);

    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}