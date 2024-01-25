package com.yexample.guestbook.service;

import com.yexample.guestbook.dto.GuestbookDTO;
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

}
