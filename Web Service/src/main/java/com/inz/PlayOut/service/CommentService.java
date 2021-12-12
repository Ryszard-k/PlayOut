package com.inz.PlayOut.service;

import com.inz.PlayOut.model.entites.Comment;
import com.inz.PlayOut.model.repositories.CommentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record CommentService(CommentRepo commentRepo) {

    public List<Comment> findAll() {
        return commentRepo.findAll();
    }

    public Optional<Comment> findById(Long id){
        return commentRepo.findById(id);
    }

    public Comment save(Comment comment){
        return commentRepo.save(comment);
    }

    public Optional<Comment> delete(Long id) {
        Optional<Comment> deleted = commentRepo.findById(id);
        if (deleted.isPresent()){
            commentRepo.deleteById(id);
            return deleted;
        } else throw new IllegalArgumentException("Not found event");
    }
}
