package com.decagon.comparators;

import com.decagon.entities.User;
import java.util.Comparator;

/**
 *
 * @author Tunde Michael 
 * @Date Jun 4, 2020
 * @Time 10:50:35 AM
 * @Quote To code is human, to debug is coffee.
 *
 */
public class SubmissionCountComparator implements Comparator<User> {

    @Override
    public int compare(User u1, User u2) {
        if (u1.getSubmissionCount() == u2.getSubmissionCount()) {
            return 0;
        } else if (u1.getSubmissionCount() > u2.getSubmissionCount()) {
            return 1;
        } else {
            return -1;
        }
    }
}
