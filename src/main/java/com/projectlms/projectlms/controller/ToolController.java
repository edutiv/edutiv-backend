package com.projectlms.projectlms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectlms.projectlms.domain.dto.ToolDto;
import com.projectlms.projectlms.service.ToolService;

@RestController
@RequestMapping(value = "/tool")
public class ToolController {
    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addTool(@RequestBody ToolDto request) {
        return toolService.addTool(request);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllTool() {
        return toolService.getAllTool();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getToolDetail(@PathVariable(value = "id") Long id) {
        return toolService.getToolDetail(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTool(@PathVariable(value = "id") Long id) {
        return toolService.deleteTool(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateTool(@PathVariable(value = "id") Long id, @RequestBody ToolDto request) {
        return toolService.updateTool(request, id);
    }
}