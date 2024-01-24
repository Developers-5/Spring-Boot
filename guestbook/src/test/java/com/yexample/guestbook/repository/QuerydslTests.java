package com.yexample.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.yexample.guestbook.GuestbookApplication;
import com.yexample.guestbook.entity.Guestbook;
import com.yexample.guestbook.entity.QGuestbook;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@Log4j2
@ContextConfiguration(classes = GuestbookApplication.class)
public class QuerydslTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void testQuery1(){
        Sort sortGnoDescending = Sort.by("gno").descending();
        Pageable pageable = PageRequest.of(0, 10, sortGnoDescending);
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(log::info);
    }

    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll)
                .and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(log::info);
    }

}
