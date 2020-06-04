package com.decagon;

import com.decagon.solutions.SocksLaundering;
import com.decagon.solutions.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DecagonApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(DecagonApplication.class, args);

            // Testing SocksLaundering Problem
            int max = 2;
            int[] clean = {1, 2, 1, 1};
            int[] dirty = {1, 4, 3, 2, 4};
            int ans = SocksLaundering.solution(max, clean, dirty);
            System.out.println("===============SocksLaundering=============");
            System.out.println(ans);
            System.out.println("==============</end>SocksLaundering========");
            System.out.println();
            
            // Testing RESTful API methods
            UserService service = new UserService();
            // 1. Author with the highest comment
            String highestCommentAuthor = service.getAuthorWithHighestCommentCount();
            System.out.println("==================Highest Comment===========");
            System.out.println(highestCommentAuthor);
            System.out.println("==================</end>Highest Comment===========");
            System.out.println();
            // 2. Most active authors
            List<String> mostActive = service.getMostActiveAuthors(2);
            System.out.println("==================Most Active Users===========");
            for (String user : mostActive) {
                System.out.println(user);
            }
            System.out.println("==================</end>Most Active Users===========");
            System.out.println();
            // 2. Users sorted by date
            List<String> users = service.getUsernamesSortedByRecordDate(2);
            System.out.println("==================Sorted By Date===========");
            for (String user : users) {
                System.out.println(user);
            }
            System.out.println("==================<end> Sorted by date ===========");
            System.out.println();
        } catch (Exception ex) {
            Logger.getLogger(DecagonApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
