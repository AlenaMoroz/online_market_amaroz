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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.gmail.marozalena.onlinemarket.service.constant.DateConstants.PATTERN_FOR_DATE;

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
        List<CommentDTO> list = Collections.singletonList(commentDTO);
        List<Comment> list1 = commentConverter.fromDTO(list);
        Assert.assertEquals(list.get(0).getId(), list1.get(0).getId());
    }

    @Test
    public void shouldConvertCommentDTOWithDateToComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setDate("2019-08-14");
        List<CommentDTO> commentsDTO = Collections.singletonList(commentDTO);
        List<Comment> comments = commentConverter.fromDTO(commentsDTO);
        Assert.assertEquals(commentsDTO.get(0).getDate(),
                new SimpleDateFormat(PATTERN_FOR_DATE).format(comments.get(0).getDate()));
    }

    @Test
    public void shouldConvertCommentDTOWithCommentToComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("comment");
        List<CommentDTO> commentsDTO = Collections.singletonList(commentDTO);
        List<Comment> comments = commentConverter.fromDTO(commentsDTO);
        Assert.assertEquals(commentsDTO.get(0).getComment(), comments.get(0).getComment());
    }

    @Test
    public void shouldConvertCommentDTOWithUserToComment() {
        CommentDTO commentDTO = new CommentDTO();
        UserDTO user = new UserDTO();
        user.setEmail("test@gmail.com");
        commentDTO.setUser(user);
        List<CommentDTO> commentsDTO = Collections.singletonList(commentDTO);
        List<Comment> comments = commentConverter.fromDTO(commentsDTO);
        Assert.assertEquals(commentsDTO.get(0).getUser().getEmail(), comments.get(0).getUser().getEmail());
    }

    @Test
    public void shouldConvertCommentWithIdToCommentDTO() {
        Comment comment = new Comment();
        comment.setId(2L);
        List<Comment> comments = Collections.singletonList(comment);
        List<CommentDTO> commentsDTO = commentConverter.toDTO(comments);
        Assert.assertEquals(commentsDTO.get(0).getId(), comments.get(0).getId());
    }

    @Test
    public void shouldConvertCommentWithDateToCommentDTO() {
        Comment comment = new Comment();
        comment.setDate(new Date());
        List<Comment> comments = Collections.singletonList(comment);
        List<CommentDTO> commentsDTO = commentConverter.toDTO(comments);
        Assert.assertEquals(commentsDTO.get(0).getDate(),
                new SimpleDateFormat(PATTERN_FOR_DATE).format(comments.get(0).getDate()));
    }

    @Test
    public void shouldConvertCommentWithCommentToCommentDTO() {
        Comment comment = new Comment();
        comment.setComment("comment");
        List<Comment> comments = Collections.singletonList(comment);
        List<CommentDTO> commentsDTO = commentConverter.toDTO(comments);
        Assert.assertEquals(commentsDTO.get(0).getComment(), comments.get(0).getComment());
    }

    @Test
    public void shouldConvertCommentWithUserToCommentDTO() {
        Comment comment = new Comment();
        User user = new User();
        user.setEmail("test@gmail.com");
        comment.setUser(user);
        List<Comment> comments = Collections.singletonList(comment);
        List<CommentDTO> commentsDTO = commentConverter.toDTO(comments);
        Assert.assertEquals(commentsDTO.get(0).getUser().getEmail(), comments.get(0).getUser().getEmail());
    }
}
