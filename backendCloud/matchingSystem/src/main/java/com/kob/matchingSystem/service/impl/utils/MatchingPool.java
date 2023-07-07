package com.kob.matchingSystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static List<Player> playerList = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    private static RestTemplate restTemplate;
    private static final String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating) {
        lock.lock();
        try {
            playerList.add(new Player(userId, rating, 0));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player> newPlayerList = new ArrayList<>();
            for (Player player : playerList) {
                if (!player.getUserId().equals(userId)) {
                    newPlayerList.add(player);
                }
            }
            playerList = newPlayerList;
        } finally {
            lock.unlock();
        }
    }


    private void increaseWaitTime() { // increase 1 second waiting time for all players
        for (Player player : playerList) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    private boolean checkMatched(Player a, Player b) { // check if two players can be matched
        int diff = Math.abs(a.getRating() - b.getRating());
        int waitTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return diff <= waitTime * 10;
    }

    private void sendResult(Player a, Player b) { // send matching result
        System.out.println("Send Resut: " + a + " " + b);
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("b_id", b.getUserId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    private void matchPlayers() {
        System.out.println("Matching players:" + playerList.toString());
        boolean[] used = new boolean[playerList.size()];
        for (int i = 0; i < playerList.size(); i++) {
            if (used[i]) {
                continue;
            }
            for (int j = i + 1; j < playerList.size(); j++) {
                if (used[j]) {
                    continue;
                }
                Player a = playerList.get(i), b = playerList.get(j);
                if (checkMatched(a, b)) {
                    used[i] = used[j] = true;
                    sendResult(a, b);
                    break;
                }
            }
        }

        List<Player> newPlayerList = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++) {
            if (!used[i]) {
                newPlayerList.add(playerList.get(i));
            }
        }
        playerList = newPlayerList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
