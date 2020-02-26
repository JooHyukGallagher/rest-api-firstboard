package me.weekbelt.restapifirstboard.domain.board;

import me.weekbelt.restapifirstboard.web.dto.board.BoardReadResponseDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveResponseDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardUpdateResponseDto;

public class BoardFactoryObject {
    public static BoardSaveResponseDto toBoardSaveResponseDto(Board board){
        return BoardSaveResponseDto.builder()
                .id(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .author(board.getAuthor())
                .boardType(board.getBoardType())
                .build();
    }

    public static BoardUpdateResponseDto toBoardUpdateResponseDto(Board board){
        return BoardUpdateResponseDto.builder()
                .id(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardType(board.getBoardType())
                .build();
    }

    public static BoardReadResponseDto toBoardReadResponseDto(Board board) {
        return BoardReadResponseDto.builder()
                .id(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .author(board.getAuthor())
                .viewCount(board.getViewCount())
                .boardType(board.getBoardType())
                .createDate(board.getCreateDate())
                .modifyDate(board.getModifyDate())
                .build();
    }
}
