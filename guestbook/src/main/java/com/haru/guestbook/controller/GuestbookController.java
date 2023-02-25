package com.haru.guestbook.controller;

import com.haru.guestbook.dto.GuestbookDTO;
import com.haru.guestbook.dto.PageRequestDTO;
import com.haru.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service; //final로 선언 (자동DI를 위해)

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list.................... : " + pageRequestDTO);
        model.addAttribute("result",service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){
        log.info("register get ...........");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto............ : " + dto);
        //시로 추가된 엔티티 번호
        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:list";
    }

    @GetMapping({"/read","/modify"})
    public void read(long gno, @ModelAttribute("requestDTO")PageRequestDTO requestDTO, Model model){
        log.info("gno........ : " + gno);
        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        log.info("gno........ : " + gno);
        service.remove(gno);
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:list";
    }
}
