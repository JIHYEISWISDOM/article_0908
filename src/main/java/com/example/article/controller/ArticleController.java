package com.example.article.controller;

import com.example.article.dto.ArticleForm;
import com.example.article.dto.CommentDto;
import com.example.article.entity.Article;
import com.example.article.repository.ArticleRepository;
import com.example.article.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ArticleController {

    @Autowired  // 스프링 부트가 미리 생성해놓은 객체를 가져다가 연결!
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        //System.out.println(form.toString());을 로깅기능으로 대체! @Slf4j
        log.info(form.toString());

        //dto를 변환 entity
        Article article = form.toEntity();
        log.info(article.toString());

        //repository에게 entity를 DB안에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) { //컨트롤에서 받아와야 함! - id라는 변수를 long타입으로 받을거 + url주소로부터 id가 입력이 된다
        log.info("id = " + id);

        //1. id로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        //2. 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        //3. 보여줄 페이지를 설정
        return "articles/show";  //articles 라는 리파지토리에 show라는 파일이 있다!
    }


    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 Article을 가져온다!
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 Article 묶음을 뷰로 전달! (이름, 던져줄 데이터)
        model.addAttribute("articleList", articleEntityList);

        //3. 뷰 페이지를 설정!
        return "articles/index"; //articles/index.mustache 파일이 뷰페이지로 설정됨
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null); //수정할 데이터를 가져오기!

        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }
    @PostMapping ("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. dto를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. 엔티티를 DB로 저장
        // 2-1. DB에서 기존 데이터를 가져온다.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터에 값을 갱신
        if (target != null) {
            articleRepository.save(articleEntity); // 엔터티가 DB로 갱신
        }
        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping ("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");


        //1. 삭제 대상을 가져온다.
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        //2. 대상을 삭제한다.
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다!");
        }

        // 3. 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles";
    }
}
