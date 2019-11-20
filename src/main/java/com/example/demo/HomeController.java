package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    MessageRepository messageRepository;

    //this method will show a list of messages stored
    //list of stuff for everything
    @RequestMapping("/")
    public String listMessages(Model model)
    {
     model.addAttribute("messages", messageRepository.findAll());
     return "listOfMessages";
    }

    @GetMapping("/add")
    public String messageForm(Model model)
    {
        model.addAttribute("message", new Message());
        return "messageForm";
    }

    @PostMapping("/process")
    public String processMessage(@Valid Message message, BindingResult result){
        if(result.hasErrors()){
            return "messageForm";
        }
        messageRepository.save(message);
        return "redirect:/";
    }

    //to view the detail of the message,using ID
    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "showDetail";
    }

     //to update the message, using ID
    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "messageForm";
    }

    //to delete the message, using ID
    @RequestMapping("/delete/{id}")
    public String deleteMessage(@PathVariable("id") long id)
    {
        messageRepository.deleteById(id);
        return "redirect:/";
    }

    //same as returning entire list, but with search function
    @PostMapping("/searchlist")
    public String search(Model model, @RequestParam("searchString") String search)
    {
        model.addAttribute("messages", messageRepository.findByContentContainingIgnoreCaseOrPostedByContainingIgnoreCase(search, search));
        return "searchlist";
    }
}
