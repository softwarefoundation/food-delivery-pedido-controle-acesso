package com.softwarefoundation.fooddeliverycontroleacesso.mensagens;

import com.softwarefoundation.fooddeliverycontroleacesso.dto.LoginDto;
import com.softwarefoundation.fooddeliverycontroleacesso.entity.Login;
import com.softwarefoundation.fooddeliverycontroleacesso.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
public class ReceiverLoginMenssage {

    @Autowired
    private LoginRepository loginRepository;


    @RabbitListener(queues = "${cadastro.login.rabbitmq.queue}")
    public void receiveMessage(@Payload LoginDto dto){
      log.info("Usuário: {}",dto.getLogin());
      Login login = Login.from(dto);
      loginRepository.save(login);
    }



}
