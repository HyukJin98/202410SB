package edu.du.sb1014_2;

import edu.du.sb1014_2.entity.Board;
import edu.du.sb1014_2.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Sb10142ApplicationTests {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void select_All() {
        for(Board board : boardRepository.findAll()) {
            System.out.println(board);
        }
    }

    @Test
    void select_All2() {
        List<Object[]> boardList = boardRepository.selectBoardList();

        for (Object[] board : boardList) {
            System.out.println("Board Index: " + board[0]);
            System.out.println("Title: " + board[1]);
            System.out.println("Hit Count: " + board[2]);
            System.out.println("Created Date: " + board[3]);
            System.out.println();
        }
    }
}
