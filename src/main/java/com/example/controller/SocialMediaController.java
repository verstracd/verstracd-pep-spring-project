package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message){
        Message newMessage = messageService.createNewMessage(message);
        return ResponseEntity.ok(newMessage);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.ok(allMessages);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        Integer rowsDeleted = messageService.deleteMessageById(messageId);
        return ResponseEntity.ok(rowsDeleted);
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        Account newAccount = accountService.register(account);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account loginAccount = accountService.login(account);
        return ResponseEntity.ok(loginAccount);
    }

}
