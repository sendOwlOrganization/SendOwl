package com.example.sendowl.domain.balance.dto;

<<<<<<< HEAD
<<<<<<< HEAD
import lombok.Builder;
=======
import com.fasterxml.jackson.annotation.JsonProperty;
>>>>>>> 7c47d86 (feat: BalanceCount)
=======
import lombok.Builder;
>>>>>>> c73d9bf (fix: rename aDetail to firstDetail, bDetail to secondDetail becasue of jsonMapping problem)
import lombok.Getter;

@Getter
public class BalanceCount {
    private Long id;
    private String title;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> c73d9bf (fix: rename aDetail to firstDetail, bDetail to secondDetail becasue of jsonMapping problem)
    private String firstDetail;
    private String secondDetail;
    private Long firstCount;
    private Long secondCount;
<<<<<<< HEAD

    @Builder
    public BalanceCount(Long id, String title, String firstDetail, String secondDetail, Long firstCount, Long secondCount) {
        this.id = id;
        this.title = title;
        this.firstDetail = firstDetail;
        this.secondDetail = secondDetail;
        this.firstCount = firstCount;
        this.secondCount = secondCount;
    }

    @Override
    public String toString() {
        return "BalanceCount{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstDetail='" + firstDetail + '\'' +
                ", secondDetail='" + secondDetail + '\'' +
                ", firstCount=" + firstCount +
                ", secondCount=" + secondCount +
                '}';
=======
    @JsonProperty("a_detail") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
    private String aDetail;
    @JsonProperty("b_detail") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
    private String bDetail;
    @JsonProperty("a_count") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
    private Long aCount;
    @JsonProperty("b_count") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
    private Long bCount;
=======
>>>>>>> c73d9bf (fix: rename aDetail to firstDetail, bDetail to secondDetail becasue of jsonMapping problem)

    @Builder
    public BalanceCount(Long id, String title, String firstDetail, String secondDetail, Long firstCount, Long secondCount) {
        this.id = id;
        this.title = title;
<<<<<<< HEAD
        this.aDetail = aDetail;
        this.bDetail = bDetail;
        this.aCount = aCount;
        this.bCount = bCount;
>>>>>>> 7c47d86 (feat: BalanceCount)
=======
        this.firstDetail = firstDetail;
        this.secondDetail = secondDetail;
        this.firstCount = firstCount;
        this.secondCount = secondCount;
>>>>>>> c73d9bf (fix: rename aDetail to firstDetail, bDetail to secondDetail becasue of jsonMapping problem)
    }

    @Override
    public String toString() {
        return "BalanceCount{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstDetail='" + firstDetail + '\'' +
                ", secondDetail='" + secondDetail + '\'' +
                ", firstCount=" + firstCount +
                ", secondCount=" + secondCount +
                '}';
    }
}