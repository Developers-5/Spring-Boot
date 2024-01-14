package org.spring.ch2.repository;

import java.util.List;
import org.spring.ch2.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
  List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

  Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

  void deleteMemoByMnoLessThan(Long num);
}