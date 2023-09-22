package org.com.jsfspring.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.com.jsfspring.service.JoinDtoService;

@AllArgsConstructor
@NoArgsConstructor
public class JoinDtoServiceImpl implements JoinDtoService {

    private Long money;
    private Long userId;
    private Long balance;
    private String firstName;

    @Override
    public Long getMoney() {
        return money;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public Long getBalance() {
        return balance;
    }
}
