// package com.Controller;
//
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// @RestController
// public class TestController {
//
//     @Autowired
//     private RabbitTemplate rabbitTemplate;
//
//     @GetMapping("/hello")
//     public String hello() {
//         rabbitTemplate.convertAndSend("hello", "Hello, World!");
//         return "Message sent.";
//     }
// }