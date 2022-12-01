package com.example.sendowl.api.service;

import com.example.sendowl.domain.blame.dto.BlameDto;
import com.example.sendowl.domain.blame.entity.Blame;
import com.example.sendowl.domain.blame.entity.BlameType;
import com.example.sendowl.domain.blame.exception.BlameTypeExistException;
import com.example.sendowl.domain.blame.exception.BlameTypeNotFoundException;
import com.example.sendowl.domain.blame.exception.enums.BlameErrorCode;
import com.example.sendowl.domain.blame.repository.BlameRepository;
import com.example.sendowl.domain.blame.repository.BlameTypeRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserException;
import com.example.sendowl.domain.user.exception.enums.UserErrorCode;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.util.mail.JwtUserParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlameService {

    private final UserRepository userRepository;
    private final BlameRepository blameRepository;
    private final BlameTypeRepository blameTypeRepository;
    private final JwtUserParser jwtUserParser;

    @Transactional
    public Long insertBlame(BlameDto.BlameReq rq) {
        BlameType blameType = blameTypeRepository.findById(rq.getBlameTypeId()).orElseThrow(
                () -> new BlameTypeNotFoundException(BlameErrorCode.NOTFOUND)
        );

        User user = jwtUserParser.getUser();

        User targetUser = userRepository.findById(rq.getTargetUserId()).orElseThrow(
                () -> new UserException.UserNotFoundException(UserErrorCode.NOT_FOUND)
        );

        return blameRepository.save(Blame.builder()
                .user(user)
                .blameType(blameType)
                .blameDetails(rq.getBlameDetails())
                .targetUser(targetUser)
                .blameContentsType(rq.getBlameContentType())
                .contentsId(rq.getContentId())
                .contentsDetails(rq.getContentDetails())
                .build()).getId();
    }

    public List<BlameDto.BlameTypeRes> getBlameTypeList() {
        List<BlameType> blameTypeList = blameTypeRepository.findAllByIsDeletedFalse();
        return blameTypeList.stream().map(BlameDto.BlameTypeRes::new).collect(Collectors.toList());
    }

    @Transactional
    public Long insertBlameType(BlameDto.BlameTypeReq rq) {
        if (blameTypeRepository.existsAllByName(rq.getBlameTypeName())) { // 이름이 있는 경우 이미 존재한다고 반환
            throw new BlameTypeExistException(BlameErrorCode.ALREADYEXIST);
        } else {
            return blameTypeRepository.save(rq.toEntity()).getId();
        }
    }

    @Transactional
    public Long deleteBlameType(Long id) {
        if (blameTypeRepository.existsById(id)) { // 존재하는 경우 삭제
            BlameType blameType = blameTypeRepository.findById(id).get();
            blameType.setDeleted(true);
            return blameType.getId();
        } else {
            throw new BlameTypeNotFoundException(BlameErrorCode.NOTFOUND);
        }
    }

    @Transactional
    public Long updateBlameType(BlameDto.BlameTypeUpdateReq rq) {
        BlameType blameType = blameTypeRepository.findById(rq.getBlameTypeId()).orElseThrow(() -> new BlameTypeNotFoundException(BlameErrorCode.NOTFOUND));
        blameType.setName(rq.getBlameTypeName());
        return blameType.getId();
    }
}
