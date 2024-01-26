package com.yexample.guestbook.service;

import com.yexample.guestbook.dto.GuestbookDTO;
import com.yexample.guestbook.dto.PageRequestDTO;
import com.yexample.guestbook.dto.PageResultDTO;
import com.yexample.guestbook.entity.Guestbook;
import com.yexample.guestbook.repository.GuestbookRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Log4j2
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Autowired
    private GuestbookRepository repository;

    @Test
    public void testRegister() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title")
                .content("Sample Content")
                .writer("user0")
                .build();

        Long gnoOfRegisteredGuestbookDto = service.register(guestbookDTO);
        log.info(gnoOfRegisteredGuestbookDto);

        Optional<Guestbook> result = repository.findById(gnoOfRegisteredGuestbookDto);
        assertTrue(result.isPresent());
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDto = service.getList(pageRequestDTO);

        log.info("PREV: " +resultDto.isPrev());
        log.info("NEXT: " +resultDto.isNext());
        log.info("TOTAL: " +resultDto.getTotalPage());

        log.info("=======================================");
        resultDto.getDtoList().forEach(log::info);

        log.info("=======================================");
        resultDto.getPageList().forEach(log::info);
    }

}
