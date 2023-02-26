package com.haru.board.repository;

import com.haru.board.entity.Board;
import com.haru.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply(){
        IntStream.rangeClosed(1,300).forEach(i->{
            //1부터 100의 임의의 번호
            long bno = (long)(Math.random()*100) + 1;
            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply..." + i)
                    .board(board)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);

        });
    }

    @Test
    public void testReply1(){

        Optional<Reply> result = replyRepository.findById(1L); //데이터베이스에 존재하는 번호

        Reply reply = result.get();
        System.out.println(reply);
        System.out.println(reply.getBoard());
    }
}