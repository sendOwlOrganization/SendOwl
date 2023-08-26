package com.example.sendowl.api.service;

import com.example.sendowl.domain.tag.entity.Tag;
import com.example.sendowl.domain.tag.exception.TagNameAlreadyExistsException;
import com.example.sendowl.domain.tag.exception.TagNotFoundException;
import com.example.sendowl.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.sendowl.domain.tag.dto.TagDto.*;
import static com.example.sendowl.domain.tag.enums.TagErrorCode.ALREADY_EXISTS;
import static com.example.sendowl.domain.tag.enums.TagErrorCode.NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagsRes> getTagList() {
        List<Tag> tagList = tagRepository.findAll();
        return tagList.stream().map(TagsRes::new).collect(Collectors.toList());
    }

    public List<TagsCountRes> getTagWithBoardCountList() {
        // TODO : 태그에 따른 게시글 카운트
//        List<TagCount> tagCountList = tagRepository.findTagsWithBoardCount();
//        return tagCountList.stream().map(TagsCountRes::new).collect(Collectors.toList());
        return null;
    }

    @Transactional
    public TagsRes insertTag(TagInsertReq rq) {
        if (tagRepository.existsByName(rq.getName())) {
            throw new TagNameAlreadyExistsException(ALREADY_EXISTS);
        }
        Tag tag = tagRepository.save(rq.toEntity());
        return new TagsRes(tag);
    }

    @Transactional
    public TagsRes updateTag(TagUpdateReq rq) {
        Tag tag = tagRepository.findById(rq.getId()).orElseThrow(
                () -> new TagNotFoundException(NOT_FOUND)
        );
        tag.setName(rq.getName());
        Tag savedTag = tagRepository.save(tag);
        return new TagsRes(savedTag);
    }

    /* TODO: 삭제하는 경우 삭제가 되었다는 응답을 주어야 할거 같다.*/
    @Transactional
    public void deleteTag(TagDeleteReq rq) {
        tagRepository.deleteById(rq.getId());
    }
}
