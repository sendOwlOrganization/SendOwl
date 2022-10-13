package com.example.sendowl.api.service;

import com.example.sendowl.domain.blame.entity.Blame;
import com.example.sendowl.domain.blame.dto.BlameType;
import com.example.sendowl.domain.blame.dto.BlameDto;
import com.example.sendowl.domain.blame.exception.BlameTypeExistException;
import com.example.sendowl.domain.blame.exception.BlameTypeNotFoundException;
import com.example.sendowl.domain.blame.exception.enums.BlameErrorCode;
import com.example.sendowl.domain.blame.repository.BlameRepository;
import com.example.sendowl.domain.blame.repository.BlameTypeRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserException;
import com.example.sendowl.domain.user.exception.enums.UserErrorCode;
import com.example.sendowl.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlameService {
    private final BlameRepository blameRepository;
    private final BlameTypeRepository blameTypeRepository;
    private final UserRepository userRepository;
    @Transactional
    public void insertBlame(BlameDto.BlameReq rq, User user){
        // 신고당한 유저의 아이디 찾기
        User targetUser = userRepository.findById(rq.getTargetUserId()).orElseThrow(() -> new UserException.UserNotFoundException(UserErrorCode.NOT_FOUND));
        Blame blame = rq.toEntity(user,targetUser);
        blameRepository.save(blame);
    }

    public List<BlameDto.BlameTypeRes> getBlameTypeList() {
        List<BlameType> blameTypeList = blameTypeRepository.findAllByIsDeletedFalse();
        return blameTypeList.stream().map(BlameDto.BlameTypeRes::new).collect(Collectors.toList());
    }

    @Transactional
    public void insertBlameType(BlameDto.BlameTypeReq rq) {
        if( blameTypeRepository.existsAllByName(rq.getName())){ // 이름이 있는 경우 이미 존재한다고 반환
            throw new BlameTypeExistException(BlameErrorCode.ALREADYEXIST);
        }else{
            blameTypeRepository.save(rq.toEntity());
        }
    }

    @Transactional
    public void deleteBlameType(Long id) {
        if (blameTypeRepository.existsById(id)) { // 존재하는 경우 삭제
            BlameType blameType = blameTypeRepository.findById(id).get();
            blameType.setDeleted(true);
        }else{
            throw new BlameTypeNotFoundException(BlameErrorCode.NOTFOUND);
        }
    }

    @Transactional
    public void updateBlameType(BlameDto.BlameTypeUpdateReq rq) {
        BlameType blameType = blameTypeRepository.findById(rq.getId()).orElseThrow(() -> new BlameTypeNotFoundException(BlameErrorCode.NOTFOUND));
        blameType.setName(rq.getName());
    }
}
