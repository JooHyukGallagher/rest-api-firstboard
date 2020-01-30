package me.weekbelt.restapifirstboard.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("RULE_USER", "일반사용자");

    private final String key;
    private final String title;
}
