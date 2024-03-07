package com.crocodile.api;

import com.crocodile.api.lesson.domain.Lesson;
import com.crocodile.api.lesson.provider.LessonVarProvider;
import com.crocodile.api.reservation.controller.command.model.ReservationPostRequest;
import com.crocodile.api.reservation.operator.ReservationOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
class LessonTests {

    @Autowired
    ReservationOperator reservationOperator;

    @Autowired
    LessonVarProvider lessonVarProvider;


    @Test
    @Transactional
    public void provider_test() {
        Lesson lesson = lessonVarProvider.get(1L, LocalDate.now().plusDays(1));
        System.out.println(lesson.getLessonName());

    }

    @Test
    public void 낙관적락_테스트() {
        // given
        int numberOfThreads = 3;
        long shopId = 1L;
        int requestCount = 5;
        String StringLocalDate = "2024-03-08";
        LocalDate localDate = LocalDate.now().plusDays(1);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        // when
        Future<?> future = executorService.submit(
                () -> reservationOperator.exe(
                        new ReservationPostRequest(shopId, 1L, requestCount, StringLocalDate)
                ));
        Future<?> future2 = executorService.submit(
                () -> reservationOperator.exe(
                        new ReservationPostRequest(shopId, 2L, requestCount, StringLocalDate)
                ));
        Future<?> future3 = executorService.submit(
                () -> reservationOperator.exe(
                        new ReservationPostRequest(shopId, 3L, requestCount, StringLocalDate)
                ));

        Exception result = new Exception();

        try {
            future.get();
            future2.get();
            future3.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // then
        Lesson lesson = lessonVarProvider.get(1L, localDate);
        Assertions.assertEquals(5, lesson.getCurrentLimit());
        Assertions.assertEquals(3L, lesson.getVersion());

    }

    @Test
    public void 낙관적락_테스트4() {
        // given
        int numberOfThreads = 3;
        long shopId = 1L;
        int requestCount = 6;
        String date = "2024-03-08";
        LocalDate localDate = LocalDate.now().plusDays(1);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        List<Callable<Void>> tasks = new ArrayList<>();
        tasks.add(() -> {
            reservationOperator.exe(new ReservationPostRequest(shopId, 1L, requestCount, date));
            return null;
        });
        tasks.add(() -> {
            reservationOperator.exe(new ReservationPostRequest(shopId, 2L, requestCount, date));
            return null;
        });
        tasks.add(() -> {
            reservationOperator.exe(new ReservationPostRequest(shopId, 3L, requestCount, date));
            return null;
        });

        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("@@@@@@@@@@");
        }

        // then
        Lesson lesson = lessonVarProvider.get(shopId, localDate);
        Assertions.assertEquals(2, lesson.getCurrentLimit());
//        Assertions.assertEquals(3, lesson.getVersion());
    }

}
