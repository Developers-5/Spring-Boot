package org.spring.ch2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.spring.ch2.entity.Memo;
import org.spring.ch2.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemoRepositoryTests {

  @Autowired MemoRepository memoRepository;

  @Test
  void testClass() {
    System.out.println(memoRepository.getClass().getName());
    assertNotNull(memoRepository);
  }

  @Test
  void testInsertDummies() {
    IntStream.rangeClosed(1, 100)
        .forEach(
            i -> {
              Memo memo = Memo.builder().memoText("Memo..." + i).build();
              memoRepository.save(memo);
              assertNotNull(memo);
            });
  }

  @Test
  void testSelect() {
    Long mno = 100L;

    Optional<Memo> result = memoRepository.findById(mno);
    System.out.println("=======================");
    if (result.isPresent()) {
      Memo memo = result.get();
      System.out.println(memo);
    }
    assertNotNull(result);
  }

  @Transactional
  @Test
  void testSelect2() {
    Long mno = 100L;

    Memo memo = memoRepository.getReferenceById(mno);
    System.out.println("=======================");

    System.out.println(memo);

    assertNotNull(memo);
  }

  @Test
  void testUpdate() {
    Memo memo = Memo.builder().mno(1L).memoText("Update Text").build();
    System.out.println(memoRepository.save(memo));
    assertEquals("Update Text", memo.getMemoText());
  }

  @Test
  void testDelete() {
    Long mno = 1L;
    memoRepository.deleteById(mno);
    assertTrue(memoRepository.findById(mno).isEmpty());
  }

  @Test
  void testPageDefault() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<Memo> result = memoRepository.findAll(pageable);
    System.out.println(result);
    assertNotNull(result);
    System.out.println("-----------------------");
    System.out.println("Total Pages: " + result.getTotalPages()); // 총 몇 페이지
    System.out.println("Total Count: " + result.getTotalElements()); // 전체 개수
    System.out.println("Page Number: " + result.getNumber()); // 현재 페이지 번호
    System.out.println("Page Size: " + result.getSize()); // 페이지당 데이터 개수
    System.out.println("has next page?: " + result.hasNext()); // 다음 페이지
    System.out.println("first page?: " + result.isFirst()); // 시작 페이지(0)여부

    System.out.println("-----------------------");
    for (Memo memo : result.getContent()) {
      System.out.println(memo);
    }
  }

  @Test
  void testSort() {
    Sort sort = Sort.by(Sort.Direction.DESC, "mno");
    Pageable pageable = PageRequest.of(0, 10, sort);
    Page<Memo> result = memoRepository.findAll(pageable);
    result.get().forEach(System.out::println);

    assertNotNull(result);
  }

  @Test
  void testQueryMethods() {
    List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

    for (Memo memo : list) {
      System.out.println(memo);
    }

    assertNotNull(list);
  }

  @Test
  void testQueryMethodWithPageable() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

    Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

    result.get().forEach(System.out::println);

    assertNotNull(result);
  }

  @Commit
  @Transactional
  @Test
  void testDeleteQueryMethods() {
    memoRepository.deleteMemoByMnoLessThan(10L);
    assertTrue(memoRepository.findAll().isEmpty());
  }
}