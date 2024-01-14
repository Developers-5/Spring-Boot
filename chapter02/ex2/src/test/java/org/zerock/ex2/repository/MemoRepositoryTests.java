package org.zerock.ex2.repository;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLJoinTableRestriction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {

        System.out.println(memoRepository.getClass().getName());
    }

    // 100개의 새로운 Memo 객체를 생성하고 MemoRepository를 이용해 insert하는 것
    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("===================================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }


    @Transactional
    @Test
    public void testSelect2() {

        //데이터베이스에 존재하는 mno
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("===================================");
        System.out.println(memo);
    }


    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));
    }


    @Test
    public void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }


    @Test
    public void testPageDefalut() {
        Pageable pagealbe = PageRequest.of(0, 10); //1페이지 데이터 10개를 가져옴

        Page<Memo> result = memoRepository.findAll(pagealbe);

        System.out.println(result);
        System.out.println("--------------------------------");
        System.out.println("Total Pages: "+result.getTotalPages()); // 총 몇 페이지
        System.out.println("Total count: "+result.getTotalElements()); // 전체 개수
        System.out.println("Page Number: "+result.getNumber()); // 현재 페이지 번호
        System.out.println("Page Size: "+result.getSize()); //페이지당 데이터 개수
        System.out.println("has next page?: "+result.hasNext()); //다음 페이지 존재 여부
        System.out.println("first page?: "+result.isFirst()); //시작 페이지(0) 여부

        System.out.println("--------------------------------");

        for(Memo memo : result.getContent()) {
            System.out.println(memo);
        }

    }


    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); //and를 이용한 연결

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });

        System.out.println("--------------------------------");
        Pageable pageable2 = PageRequest.of(0, 10, sortAll); // 결합된 정렬 조건 사용

        Page<Memo> result2 = memoRepository.findAll(pageable2);

        result2.get().forEach(memo -> {
            System.out.println(memo);
        });
    }


    @Test
    public void testQueryMethods() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for(Memo memo : list) {
            System.out.println(memo);
        }
    }


}
