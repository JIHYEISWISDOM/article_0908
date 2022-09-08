package com.example.article.api;

import com.example.article.dto.ArticleForm;
import com.example.article.entity.Article;
import com.example.article.repository.ArticleRepository;
import com.example.article.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired //DI 리파지토리 필드 만들어 줘야지 쓸 수 있으니까 만들어주는데, 외부에서 가지고 온다는 뜻인 오토와이어드 씀
    private ArticleService articleService;     //Autowired는 DI  (생성 객체를 가져와 연결)

    // 목록 조회 GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    //Article -> RespondeEntity<Article>로 바꾸면 상태코드를 실어서 보내줄 수 있음
    public ResponseEntity<Article> index(@PathVariable Long id,
                                         @RequestBody ArticleForm dto) {

        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
       Article deleted = articleService.delete(id);
       return (deleted != null) ?
               ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
               ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }

      //트랜잭션 -> 실패 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    }
