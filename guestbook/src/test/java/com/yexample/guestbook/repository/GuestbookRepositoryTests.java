package com.yexample.guestbook.repository;

import com.yexample.guestbook.GuestbookApplication;
import com.yexample.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.IntStream;

@SpringBootTest
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
            System.out.println(guestbookRepository.save(guestbook));
        });

    }

}
