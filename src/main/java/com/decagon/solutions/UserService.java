package com.decagon.solutions;

import com.decagon.DecagonApplication;
import com.decagon.comparators.CreatedByComparator;
import com.decagon.comparators.SubmissionCountComparator;
import com.decagon.entities.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Tunde Michael
 * @Date Jun 4, 2020
 * @Time 11:10:48 AM
 * @Quote To code is human, to debug is coffee.
 *
 */
public class UserService {

    private final List<User> users;

    public UserService() {
        users = getUsers();
    }

    public List<String> getMostActiveAuthors(int threshold) {
        if (users == null) {
            return new ArrayList<>();
        }
        Collections.sort(users, new SubmissionCountComparator());
        List<String> usernames = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (i <= threshold) {
                usernames.add(users.get(i).toString());
            } else {
                break;
            }
        }
        return usernames;
    }

    public String getAuthorWithHighestCommentCount() {
        User highestCounter = users.get(0);
        for (User u : users) {
            if (u.getCommentCount() > highestCounter.getCommentCount()) {
                highestCounter = u;
            }
        }
        return highestCounter.toString();
    }

    public List<String> getUsernamesSortedByRecordDate(int threshold) {
        if (users == null) {
            return new ArrayList<>();
        }
        Collections.sort(users, new CreatedByComparator());
        List<String> usernames = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (i <= threshold) {
                usernames.add(users.get(i).toString());
            } else {
                break;
            }
        }
        return usernames;
    }

    private List<User> getUsers() {
        int page = 1;
        List<User> userList = new ArrayList<>();
        // Get and process the first page
        String ans = getUsers(page);
        processJson(userList, ans);
        // Check for more pages
        JSONObject json = new JSONObject(ans);
        int totalPages = json.getInt("total_pages");
        while (page < totalPages) {
            page = page + 1;
            ans = getUsers(page);
            processJson(userList, ans);
        }
        return userList;
    }

    private String getUsers(int page) {
        String ans;
        try {
            String host = "https://jsonmock.hackerrank.com/api/article_users/search?page=";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            ResponseEntity<String> response = restTemplate.exchange(host + page, HttpMethod.GET,
                    new HttpEntity<>("parameters", headers), String.class);
            ans = response.getBody();
            return ans;
        } catch (RestClientException e) {
            JSONObject error = new JSONObject();
            try {
                error.put("error", "Sorry. I'm not able to connect to the internet. Please try again!");
            } catch (JSONException ex) {
                Logger.getLogger(DecagonApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(DecagonApplication.class.getName()).log(Level.SEVERE, null, e);
            ans = error.toString();
        }
        return ans;
    }

    private User userFromJson(String ans) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            JSONObject json = new JSONObject(ans);
            User user = new User();
            user.setId(json.getInt("id"));
            user.setUsername(json.getString("username"));
            user.setAbout(json.getString("about"));
            user.setSubmitted(json.getInt("submitted"));
            user.setSubmissionCount(json.getInt("submission_count"));
            user.setCommentCount(json.getInt("submitted"));
            user.setCreatedAt(new Date(json.getLong("created_at")));
            String updatedAtStr = json.getString("updated_at");
            Date updatedAt = sdf.parse(updatedAtStr);
            user.setUpdatedAt(updatedAt);
            return user;
        } catch (ParseException ex) {
            Logger.getLogger(DecagonApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<User> processJson(List<User> users, String ans) {
        JSONObject json = new JSONObject(ans);
        JSONArray data = json.getJSONArray("data");
        if (data != null) {
            for (int i = 0; i < data.length(); i++) {
                JSONObject dt = data.getJSONObject(i);
                User user = userFromJson(dt.toString());
                if (user != null && !users.contains(user)) {
                    users.add(user);
                }
            }
        }
        return users;
    }

}
