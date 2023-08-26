package com.bhaskar.quizservice.service;


import com.bhaskar.quizservice.client.QuestionClient;
import com.bhaskar.quizservice.dao.QuizDao;
import com.bhaskar.quizservice.model.QuestionWrapper;
import com.bhaskar.quizservice.model.Quiz;
import com.bhaskar.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    QuestionClient questionClient;

    public ResponseEntity<String> createQuiz(String category, int numQ, String quizTitle) {


        List<Integer> questionIds = questionClient.getQuestionsForQuiz(category, numQ);//call  question service generate url
        Quiz quiz = new Quiz();
        quiz.setQuizTitle(quizTitle);
        quiz.setQuestionIds(questionIds);
        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {


        Quiz quiz = quizDao.findById(quizId).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionsFromId = questionClient.getQuestionsFromId(questionIds);
        return questionsFromId;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        ResponseEntity<Integer> score = questionClient.getScore(responses);
        return score;

    }
}


