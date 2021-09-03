package com.example.blogapp.api.response;

import com.example.blogapp.entity.Post;
import com.example.blogapp.repository.PostRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CalendarResponse {

    Set<Integer> years = new HashSet<>();
    Map<String, Integer> posts = new HashMap<>();

    public CalendarResponse() {
    }

    public CalendarResponse(PostRepo postRepo) {
        init(postRepo);
    }

    public CalendarResponse(HashSet<Integer> years, HashMap<String, Integer> posts) {
        this.years = years;
        this.posts = posts;
    }

    public Set<Integer> getYears() {
        return years;
    }

    public void setYears(HashSet<Integer> years) {
        this.years = years;
    }

    public Map<String, Integer> getPosts() {
        return posts;
    }

    public void setPosts(HashMap<String, Integer> posts) {
        this.posts = posts;
    }

    public void init(PostRepo postRepo) {
        ArrayList<LocalDateTime> times = (ArrayList<LocalDateTime>) postRepo.createDates();
        if (!times.isEmpty()) {
            years = times.stream()
                    .collect(Collectors.groupingBy(LocalDateTime::getYear))
                    .keySet();
        } else {
            years.add(2002); //Маркер, что что-то не работает
        }

        posts = countPostsByDate(times);
    }

    private HashMap<String, Integer> countPostsByDate(ArrayList<LocalDateTime> timeList) {
        HashMap<String, Integer> resultMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<LocalDate, String> t = timeList.stream().collect(Collectors.toMap(
                LocalDateTime::toLocalDate,
                date -> date.format(formatter),
                (a, b) -> String.join(" ", a, b)));
        for (LocalDate date : t.keySet()) {
            String string = t.get(date);
            String temp = string.replace(date.toString(), "");
            int occ = (string.length() - temp.length()) / date.toString().length();
            resultMap.put(date.toString(), occ);
        }
        return resultMap;
    }

    public ArrayList<Post> filterPostsByYear(ArrayList<Post> postsList, int year) {
        if (year != 0) {
            return (ArrayList<Post>) postsList.stream()
                    .filter(post -> post.getTime().getYear() == year)
                    .collect(Collectors.toList());
        } else {
            return postsList;
        }
    }

    public HashMap<String, Integer> postMapByDate(ArrayList<Post> postList) {
        HashMap<String, Integer> map = new HashMap<>();
        for (Post post : postList) {
            LocalDateTime postTime = post.getTime();
            LocalDate dateForGroup = LocalDate.of(postTime.getYear(), postTime.getMonth(), postTime.getDayOfMonth());
            int count = (int) postList.stream()
                    .filter(p ->
                            (p.getTime().isAfter(ChronoLocalDateTime.from(dateForGroup.minusDays(1L))) &&
                                    p.getTime().isBefore(ChronoLocalDateTime.from(dateForGroup.plusDays(1L)))))
                    .count();
            map.put(dateForGroup.toString(), count);
        }
        return map;
    }
}
