package com.yexample.guestbook.service;

import com.yexample.guestbook.dto.GuestbookDTO;
import com.yexample.guestbook.dto.PageRequestDTO;
import com.yexample.guestbook.dto.PageResultDTO;
import com.yexample.guestbook.entity.Guestbook;
import com.yexample.guestbook.repository.GuestbookRepository;
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
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO register ............ ");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);
        log.info(entity);

        repository.save(entity);
        return entity.getGno();
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());
        result.ifPresent(guestbookEntity -> {
            guestbookEntity.changeTitle(dto.getTitle());
            guestbookEntity.changeContent(dto.getContent());
            repository.save(guestbookEntity);
        });
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        log.info("requestDTO: " + requestDTO);
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> pageGuestbook = repository.findAll(pageable);

        // Page<Guestbook> -> PageResultDTO
        // PageResultDTO.dtoList -> List<GuestbookDTO>
        return new PageResultDTO<>(pageGuestbook, this::entityToDto);
    }


}
