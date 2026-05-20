package com.sharafatdin.talgatbek.taskmanager.service;

import com.sharafatdin.talgatbek.taskmanager.dto.request.ProjectRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.ProjectResponse;
import java.util.List;

/** @author Talgatbek Sharafatdin */
public interface TalgatbekProjectService {
    ProjectResponse createProject(ProjectRequest request, String username);
    ProjectResponse getProjectById(Long id);
    List<ProjectResponse> getAllProjects();
    List<ProjectResponse> getMyProjects(String username);
    ProjectResponse updateProject(Long id, ProjectRequest request, String username);
    void deleteProject(Long id, String username);
}
