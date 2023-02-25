package com.haru.guestbook.service;

import com.haru.guestbook.dto.GuestbookDTO;
import com.haru.guestbook.dto.PageRequestDTO;
import com.haru.guestbook.dto.PageResultDTO;
import com.haru.guestbook.entity.Guestbook;
import com.haru.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor //의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository; //반드시 final로 선언

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO --------------- :" + dto);

        Guestbook entity = dtoToEntity(dto);

        log.info("ENTITY ------------ : " + entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook,GuestbookDTO> fn = (this::entityToDto);

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {

        Optional<Guestbook> result = repository.findById(dto.getGno());
        if (result.isPresent()){
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }

    }
}
