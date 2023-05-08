package com.listam.controller;

import com.listam.entity.Comment;
import com.listam.entity.Item;
import com.listam.repository.CommentRepository;
import com.listam.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemRepository itemRepository;
    @PostMapping("/addComment")
    public String addComment(@RequestParam("itemId") int id, @RequestParam("comment") String com){
        Optional<Item> byId = itemRepository.findById(id);
        if(byId.isPresent()) {
            Comment comment = new Comment();
            comment.setComment(com);
            comment.setItem(byId.get());
            commentRepository.save(comment);
        }
        return "redirect:/items/" + id;
    }

    @GetMapping("/removeComment")
    public String removeComment(@RequestParam("comId") int id){
        int itemId = commentRepository.findById(id).get().getItem().getId();
        commentRepository.deleteById(id);
        return "redirect:/items/" + itemId;
    }
}
