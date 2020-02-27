package me.weekbelt.restapifirstboard.config.auth.dto;

import lombok.Getter;
import me.weekbelt.restapifirstboard.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getName();
        this.picture = user.getPicture();
    }
}
