package com.yexample.guestbook.service;

import com.yexample.guestbook.dto.GuestbookDTO;
import com.yexample.guestbook.dto.PageRequestDTO;
import com.yexample.guestbook.dto.PageResultDTO;
import com.yexample.guestbook.entity.Guestbook;

public interface GuestbookService {

    Long register(GuestbookDTO dto);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        return Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
    }

    default GuestbookDTO entityToDto(Guestbook entity){
        return GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

}
