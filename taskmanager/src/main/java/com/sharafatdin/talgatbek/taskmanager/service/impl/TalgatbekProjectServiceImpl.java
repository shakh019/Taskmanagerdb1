package com.sharafatdin.talgatbek.taskmanager.service.impl;

import com.sharafatdin.talgatbek.taskmanager.dto.request.ProjectRequest;
import com.sharafatdin.talgatbek.taskmanager.dto.response.ProjectResponse;
import com.sharafatdin.talgatbek.taskmanager.entity.*;
import com.sharafatdin.talgatbek.taskmanager.exception.*;
import com.sharafatdin.talgatbek.taskmanager.mapper.TalgatbekProjectMapper;
import com.sharafatdin.talgatbek.taskmanager.repository.*;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/** @author Talgatbek Sharafatdin */
@Service @RequiredArgsConstructor @Slf4j
public class TalgatbekProjectServiceImpl implements TalgatbekProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TalgatbekProjectMapper projectMapper;

    @Override @Transactional
    public ProjectResponse createProject(ProjectRequest request, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : Project.ProjectStatus.ACTIVE)
                .owner(owner).build();
        log.info("Creating project: {} by {}", request.getName(), username);
        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        return projectMapper.toResponse(projectRepository.findById(id)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Project", id)));
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::toResponse).toList();
    }

    @Override
    public List<ProjectResponse> getMyProjects(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        return projectRepository.findByOwnerId(user.getId()).stream().map(projectMapper::toResponse).toList();
    }

    @Override @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request, String username) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Project", id));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        if (!project.getOwner().getId().equals(user.getId()) && user.getRole() != User.Role.ROLE_ADMIN) {
            throw new TalgatbekUnauthorizedException("Not authorized to update this project");
        }
        if (request.getName() != null) project.setName(request.getName());
        if (request.getDescription() != null) project.setDescription(request.getDescription());
        if (request.getStatus() != null) project.setStatus(request.getStatus());
        log.info("Updated project id: {}", id);
        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override @Transactional
    public void deleteProject(Long id, String username) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("Project", id));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TalgatbekResourceNotFoundException("User not found"));
        if (!project.getOwner().getId().equals(user.getId()) && user.getRole() != User.Role.ROLE_ADMIN) {
            throw new TalgatbekUnauthorizedException("Not authorized to delete this project");
        }
        projectRepository.deleteById(id);
        log.info("Deleted project id: {}", id);
    }
}
