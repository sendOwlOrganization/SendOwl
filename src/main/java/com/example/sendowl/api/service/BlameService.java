package com.example.sendowl.api.service;

import com.example.sendowl.domain.blame.Blame;
import com.example.sendowl.domain.blame.dto.BlameDto;
import com.example.sendowl.domain.blame.repository.BlameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlameService {
    private final BlameRepository blameRepository;
    public void insertBlame(BlameDto.BlameReq rq){

        Blame blame = rq.toEntity();
        System.out.println("blame:" + blame.toString());
        blameRepository.save(blame);
    }
}
