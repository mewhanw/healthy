package com.paper.healthy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String vvv = new String("data:image/jpeg;base64,uJgI3909dE7poyaNK8a1RjBvnCa2WlWDuOdWWV29aMvK4d68Q1QCnV5/qkZtZ616gx722Bfce3BHzXom1952rrW0owRK3TyHGMY4jXjvUZVcf6O1luj0Zf41Zdv98jVn3n7wGN7feEdvyo66YfEyYfiIiILq/Lk3xwfjNCSj74fiPB/ZsPMtdjRH0TQmPuawyj74D/D7HjZT0Y+3lmAAAAAElFTkSuQmCC");
        System.out.println("vvvlenth:"+vvv.length());
        System.out.println("byte:"+vvv.getBytes());
        System.out.println("string:"+new String(vvv.getBytes()));
    }
}