package com.bhaskar.quizservice.controller;
import com.bhaskar.quizservice.model.*;
import com.bhaskar.quizservice.service.QuizService;
import com.bhaskar.quizservice.dao.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("quiz")
public class QuizController {


    @Autowired
     QuizService quizService;
    @Autowired
     QuizDao quizDao;

    @GetMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {

        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQuestions(), quizDto.getQuizTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable(name = "id") Integer quizId) {

           return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }

}
