package com.softwarefoundation.fooddeliverycontroleacesso.entity;

import com.softwarefoundation.fooddeliverycontroleacesso.dto.LoginDto;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "TB01_LOGIN")
@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "SENHA")
    private String senha;


    public static Login from(LoginDto dto){
        Login login = new Login();
        login.setLogin(dto.getLogin());
        login.setSenha(dto.getSenha());
        return login;
    }

}
