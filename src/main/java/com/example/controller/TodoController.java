package com.example.controller;

import com.example.domain.Todo;
import com.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public List<Todo> list() {
        return this.todoRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    public Todo get(@PathVariable("id") long id){
        return this.todoRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Todo add(@RequestBody Todo todo){
        Todo model = new Todo();
        model.setTitle(todo.getTitle());
        model.setDescription(todo.getDescription());
        return this.todoRepository.save(model);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Todo update(@RequestBody @Valid Todo todo) {
        return this.todoRepository.save(todo);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        this.todoRepository.delete(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
