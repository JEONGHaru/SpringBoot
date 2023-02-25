package com.haru.guestbook.service;

import com.haru.guestbook.dto.GuestbookDTO;
import com.haru.guestbook.dto.PageRequestDTO;
import com.haru.guestbook.dto.PageResultDTO;
import com.haru.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content")
                .writer("user0")
                .build();
        System.out.println(service.register(dto));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());
        System.out.println("---------------------------------------------------");

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList() ) {
            System.out.println(guestbookDTO);
        }
        System.out.println("------------------------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));



    }
}