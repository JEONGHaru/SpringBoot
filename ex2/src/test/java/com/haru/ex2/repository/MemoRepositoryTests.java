package com.haru.ex2.repository;

import com.haru.ex2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoRepositoryTests {

    @Autowired
    MemoRepository repository;

    @Test
    public void testClass(){
        System.out.println(repository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("Sample.." + i)
                    .build();
            repository.save(memo);
        });
    }

    @Test
    public void testSelect(){
        Long mno = 100L;
        Optional<Memo> result = repository.findById(mno);
        System.out.println("=============================");
        if (result.isPresent()){
            Memo memo = result.get();
            System.out.println("memo : " + memo);
        }
    }
    @Transactional
    @Test
    public void testSelect2(){
        Long mno = 100L;
        Memo memo = repository.getOne(mno);
        System.out.println("=============================");
        System.out.println("memo : " + memo);

    }

    @Test
    public void testUpdate(){

        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(repository.save(memo));
    }

    @Test
    public void testDelete(){
        Long mno = 100L;
        repository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
        //1~10페이지
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = repository.findAll(pageable);
        System.out.println("result : "+result);
        System.out.println("-----------------------------------------");

        System.out.println("Total Pages : " + result.getTotalPages()); //총 몇 페이지
        System.out.println("Total Count : " + result.getTotalElements()); //전체 갯수
        System.out.println("Page Number : " + result.getNumber()); // 현재 페이지 번호 0부터 시작
        System.out.println("Page Size : " + result.getSize()); //페이지당 데이터 개수
        System.out.println("has Next Page? : " + result.hasNext()); //다음 페이지 존재여부
        System.out.println("First Page? : " + result.isFirst()); //시작페이지(0) 여부

        System.out.println("----------------------------------------");
        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }

    }

    @Test
    public void testSort(){

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); //and를 이용한 연결

        Pageable pageable = PageRequest.of(0,10,sortAll);
        Page<Memo> result = repository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods(){

        List<Memo> list = repository.findByMnoBetweenOrderByMnoDesc(70L,80L);
        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Memo> result = repository.findByMnoBetween(10L,50L,pageable);
        result.get().forEach(memo -> System.out.println(memo));
    }

    @Test
    @Transactional
    @Commit
    public void testDeleteQueryMethods(){
        repository.deleteMemoByMnoLessThan(10L);
    }
}