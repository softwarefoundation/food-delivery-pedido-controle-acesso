package com.softwarefoundation.fooddeliverycontroleacesso.jwt.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String login;
    private String senha;


}
