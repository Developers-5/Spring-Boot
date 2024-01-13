package org.zerock.ex2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // JPA가 관리
@Table(name= "tb1_memo") // Entity와 매핑할 테이블 지정
@ToString
@Getter
@Builder // 객체 생성
@AllArgsConstructor
@NoArgsConstructor // 빌더를 이용하기 위해 2개다 사용
public class Memo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 자동생성 및 PK 자동생성
	private Long mno;
	
	@Column(length = 200, nullable = false)
	private String memoText;

}
