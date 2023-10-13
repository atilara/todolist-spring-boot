package br.com.atilara.todolist.task;

import br.com.atilara.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var userId = (UUID) request.getAttribute("userId");
        taskModel.setUserId(userId);

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.badRequest().body("A data de início não pode ser maior que a data de término");
        }

        return ResponseEntity.ok(this.taskRepository.save(taskModel));
    }

    @GetMapping("/")
    public ResponseEntity list (HttpServletRequest request) {
        var userId = (UUID) request.getAttribute("userId");
        return ResponseEntity.ok(this.taskRepository.findByUserId(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var userId = (UUID) request.getAttribute("userId");
        var task = this.taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        if (!task.getUserId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        Utils.copyNonNullProperties(taskModel, task);

        return ResponseEntity.ok(this.taskRepository.save(task));
    }

}

