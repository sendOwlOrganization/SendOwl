package com.example.sendowl.api.service;

import com.example.sendowl.domain.tag.dto.TagDto;
import com.example.sendowl.domain.tag.entity.Tag;
import com.example.sendowl.domain.tag.exception.TagNameAlreadyExistsException;
import com.example.sendowl.domain.tag.exception.TagNotFoundException;
import com.example.sendowl.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    private final Long CATEGORY_ID = 1L;
    private final Long CATEGORY_ID2 = 2L;
    private final String CATEGORY_NAME1 = "자유게시판";
    private final String CATEGORY_NAME2 = "기타";
    @InjectMocks
    private TagService tagService;
    @Mock
    private TagRepository tagRepository;
    private Tag tag;
    private Tag tag2;

    @BeforeEach
    void setUp() {
        tag = Tag.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME1).build();
        tag2 = Tag.builder()
                .id(CATEGORY_ID2)
                .name(CATEGORY_NAME2).build();
    }

    @Test
    void when_insertCategoryWithAlreadyExist_then_categoryNameAlreadyExistsException() {
        // given
        TagDto.TagInsertReq req = new TagDto.TagInsertReq();
        when(tagRepository.existsByName(any())).thenReturn(true);
        // when
        // then
        Assertions.assertThrows(TagNameAlreadyExistsException.class, () -> {
            tagService.insertTag(req);
        });
    }

    @Test
    void when_insertCategory_then_categoryNameAlreadyExistsException() {
        // given
        TagDto.TagInsertReq req = new TagDto.TagInsertReq();
        when(tagRepository.existsByName(any())).thenReturn(false);
        when(tagRepository.save(any())).thenReturn(tag);
        // when
        TagDto.TagsRes tagsRes = tagService.insertTag(req);
        // then
        assertEquals(tagsRes.getName(), tag.getName());
    }

    @Test
    void when_getCategoryListEmpty_then_getCategoryList() {
        // given
        List<Tag> categories = new ArrayList<>();
        when(tagRepository.findAll()).thenReturn(categories);
        // when
        List<TagDto.TagsRes> categoryList = tagService.getTagList();
        // then
        assertEquals(categoryList.size(), 0);
    }

    @Test
    void when_getCategoryList_then_getCategoryList() {
        // given
        List<Tag> categories = new ArrayList<>();
        categories.add(tag);
        categories.add(tag2);
        when(tagRepository.findAll()).thenReturn(categories);
        // when
        List<TagDto.TagsRes> categoryList = tagService.getTagList();
        // then
        assertEquals(categoryList.size(), 2);
        assertEquals(categoryList.get(0).getId(), tag.getId());
    }
    
//    @Test
//    void when_getCategoryWithBoardCountListWith_then_getCategoryList() {
//        // given
//        List<TagCount> categories = new ArrayList<>();
//        when(tagRepository.findTagsWithBoardCount()).thenReturn(categories);
//        // when
//        List<TagDto.TagsCountRes> categoryCountList = tagService.getTagWithBoardCountList();
//        // then
//        assertEquals(categoryCountList.size(), 0);
//    }
//
//    @Test
//    void when_getCategoryWithBoardCountListWithEmpty_then_getCategoryList() {
//        // given
//        List<TagCount> categories = new ArrayList<>();
//        categories.add(new TagCount(tag.getId(), tag.getName(), 1L));
//        categories.add(new TagCount(tag2.getId(), tag2.getName(), 2L));
//        when(tagRepository.findTagsWithBoardCount()).thenReturn(categories);
//        // when
//        List<TagDto.TagsCountRes> categoryCountList = tagService.getTagWithBoardCountList();
//        // then
//        assertEquals(categoryCountList.size(), 2);
//        assertEquals(categoryCountList.get(0).getId(), categories.get(0).getTagId());
//    }

    @Test
    void when_updateCategoryEmpty_then_CategoryNotFoundException() {
        // given
        TagDto.TagUpdateReq req = new TagDto.TagUpdateReq();
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // when
        // then
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            tagService.updateTag(req);
        });
    }

    @Test
    void when_updateCategory_then_getCategoryRes() {
        // given
        TagDto.TagUpdateReq req = new TagDto.TagUpdateReq(tag2.getId(), tag2.getName());
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(tag));
        when(tagRepository.save(any())).thenReturn(tag2);
        // when
        TagDto.TagsRes tagsRes = tagService.updateTag(req);
        // then
        assertEquals(tagsRes.getId(), tag2.getId());
        assertEquals(tagsRes.getName(), tag2.getName());
    }

    @Test
    void when_deleteCategory_then_void() {
        // given
        TagDto.TagDeleteReq req = new TagDto.TagDeleteReq();
        // when
        tagService.deleteTag(req);
        // then
    }
}