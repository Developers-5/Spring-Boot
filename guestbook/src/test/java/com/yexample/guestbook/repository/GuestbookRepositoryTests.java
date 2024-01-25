package com.yexample.guestbook.repository;

import com.yexample.guestbook.GuestbookApplication;
import com.yexample.guestbook.entity.Guestbook;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Log4j2
@ContextConfiguration(classes = GuestbookApplication.class)
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach( i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title....." + i)
                    .content("Content....." + i)
                    .writer("user....." + (i%10))
                    .build();
            log.info(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {
        Long testId = 300L;
        Optional<Guestbook> result = guestbookRepository.findById(testId);
        String changeTitle = "Change Title....";
        String changeContent = "Change Content....";

        result.ifPresent( guestbook -> {
            guestbook.changeTitle(changeTitle);;
            guestbook.changeContent(changeContent);;
            guestbookRepository.save(guestbook);
        });

        guestbookRepository.findById(300L).ifPresent( updatedGuestBook -> {
            assertEquals(updatedGuestBook.getTitle(), changeTitle);
            assertEquals(updatedGuestBook.getContent(), changeContent);
        });
    }

}
