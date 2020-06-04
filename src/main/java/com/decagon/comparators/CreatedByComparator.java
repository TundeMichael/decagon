package com.decagon.comparators;

import com.decagon.entities.User;
import java.util.Comparator;

/**
 *
 * @author Tunde Michael
 * @Date Jun 4, 2020
 * @Time 10:51:26 AM
 * @Quote To code is human, to debug is coffee.
 *
 */
public class CreatedByComparator implements Comparator<User> {

    @Override
    public int compare(User u1, User u2) {
        return u1.getCreatedAt().compareTo(u2.getCreatedAt());
    }

}
