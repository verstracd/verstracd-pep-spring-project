package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createNewMessage(Message message){
        if(message.getMessageText() == null || 
        message.getMessageText().isEmpty() ||
        message.getMessageText().length() > 255){
            throw new BadRequestException("Message text is blank or over 255 characters.");
        }
        if(!accountRepository.existsById(message.getPostedBy())){
            throw new BadRequestException("User does not exist: " + message.getPostedBy());
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id){
        return messageRepository.findById(id).orElse(null);
    }

    public Integer deleteMessageById(int id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return 1;
        }else{
            return null;
        }
        
    }

    @Transactional
    public Integer updateMessageById(int id, String messageText){
        if(messageText == null || 
        messageText.isBlank() ||
        messageText.length() > 255){
            throw new BadRequestException("Message text is blank or over 255 characters.");
        }
        if(messageRepository.existsById(id)){
            Message message = messageRepository.getById(id);
            message.setMessageText(messageText);
            return 1;
        }else{
            throw new BadRequestException("Message ID does not exist: " + id);
        }
    }

    public List<Message> getMessagesByAccountId(int postedBy){
        return messageRepository.findByPostedBy(postedBy);
    }
}
