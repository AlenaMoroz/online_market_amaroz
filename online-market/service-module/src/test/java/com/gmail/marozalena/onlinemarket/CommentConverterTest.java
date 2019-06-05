package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.repository.model.Comment;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.converter.CommentConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.CommentConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ProfileConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.RoleConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.UserConverterImpl;
import com.gmail.marozalena.onlinemarket.service.model.CommentDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CommentConverterTest {

    private CommentConverter commentConverter;

    @Before
    public void init() {
        commentConverter = new CommentConverterImpl(
                new UserConverterImpl(
                        new RoleConverterImpl(), new ProfileConverterImpl()));
    }

    @Test
    public void shouldConvertCommentDTOWithIdToComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(2L);
        commentDTO.setDate("2019-08-14");
        commentDTO.setComment("comment");
        commentDTO.setUser(new UserDTO());
        List<CommentDTO> list = Collections.singletonList(commentDTO);
        List<Comment> list1 = commentConverter.fromDTO(list);
        Assert.assertEquals(list.get(0).getComment(), list1.get(0).getComment());
    }

    @Test
    public void shouldConvertCommentWithIdToCommentDTO() {
        Comment comment = new Comment();
        comment.setId(2L);
        comment.setDate(new Date());
        comment.setComment("comment");
        comment.setUser(new User());
        List<Comment> list = Collections.singletonList(comment);
        List<CommentDTO> list1 = commentConverter.toDTO(list);
        Assert.assertEquals(list1.get(0).getComment(), list.get(0).getComment());
    }
}
