package com.listam.controller;

import com.listam.entity.Category;
import com.listam.entity.Comment;
import com.listam.entity.Item;
import com.listam.repository.CategoryRepository;
import com.listam.repository.CommentRepository;
import com.listam.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/items")
    public String itemsPage(ModelMap modelMap) {
        List<Item> all = itemRepository.findAll();
        modelMap.addAttribute("items", all);
        return "items";
    }

    @GetMapping("/items/{id}")
    public String singleItemPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Item> byId = itemRepository.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            List<Comment> comments = commentRepository.findAll();

            List<Comment> filteredComments = comments.stream()
                    .filter(comment -> comment.getItem().getId() == item.getId())
                    .toList();

            modelMap.addAttribute("comments", filteredComments);
            modelMap.addAttribute("item", item);
            return "singleItem";
        } else {
            return "redirect:/items";
        }
    }

    @GetMapping("/items/add")
    public String itemsAddPage(ModelMap modelMap) {
        List<Category> all = categoryRepository.findAll();
        modelMap.addAttribute("categories", all);
        return "addItem";
    }

    @PostMapping("/items/add")
    public String itemsAdd(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items/remove")
    public String removeCategoryPage(@RequestParam("id") int id) {
        itemRepository.deleteById(id);
        return "redirect:/items";
    }
}
