package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle; // 아이디

    private String password; // 초기 : 자신의 bojHandle

    private String roles; // USER, ADMIN

    private String notionId;

    private Boolean manager;

    private Integer warning;

    private String profileImg; // by Solved

    private String emoji;

    private Integer tier; // by Solved

    private Integer totalSolved; // by Solved

    private Integer currentStreak; // by Solved

    private Integer currentRandomStreak;

    private Integer team;

    private Integer point;

    private Boolean isTodaySolved; // by Solved

    private Boolean isTodayRandomSolved;

    private Integer todaySolvedProblemCount; // by Solved


    /*
     * 유저 Role Parser
     */
    public List<String> getRolesList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public User(String bojHandle, String password, String roles, String notionId, Boolean manager, Integer warning, String profileImg, String emoji, Integer tier, Integer totalSolved, Integer currentStreak, Integer currentRandomStreak, Integer team, Integer point, Boolean isTodaySolved, Boolean isTodayRandomSolved, Integer todaySolvedProblemCount) {
        this.bojHandle = bojHandle;
        this.password = password;
        this.roles = roles;
        this.notionId = notionId;
        this.manager = manager;
        this.warning = warning;
        this.profileImg = profileImg;
        this.emoji = emoji;
        this.tier = tier;
        this.totalSolved = totalSolved;
        this.currentStreak = currentStreak;
        this.currentRandomStreak = currentRandomStreak;
        this.team = team;
        this.point = point;
        this.isTodaySolved = isTodaySolved;
        this.isTodayRandomSolved = isTodayRandomSolved;
        this.todaySolvedProblemCount = todaySolvedProblemCount;
    }

    public void increaseWarning() {
        if (this.warning < 4)
            this.warning++;
    }

    public void decreaseWarning() {
        if (this.warning > 1)
            this.warning--;
    }

    public Boolean increasePoint(Integer value) {
        this.point += value;
        return true;
    }

    public Boolean decreasePoint(Integer value) {
        if (this.point < value) return false;
        this.point -= value;
        return true;
    }

    public void checkTodayRandomSolvedOk() {
        this.isTodayRandomSolved = true;
    }

    public void checkTodayRandomSolvedNo() {
        this.isTodayRandomSolved = false;
    }

    public void increaseCurrentRandomStreak() {
        this.currentRandomStreak++;
    }

    public void resetCurrentRandomStreak() {
        this.currentRandomStreak = 0;
    }

    public UserInfoResponse toUserInfoResponse() {
        return UserInfoResponse.builder()
                .bojHandle(this.getBojHandle())
                .notionId(this.getNotionId())
                .manager(this.getManager())
                .warning(this.getWarning())
                .profileImg(this.getProfileImg())
                .emoji(this.getEmoji())
                .tier(this.getTier())
                .totalSolved(this.getTotalSolved())
                .currentStreak(this.getCurrentStreak())
                .currentRandomStreak(this.getCurrentRandomStreak())
                .team(this.getTeam())
                .point(this.getPoint())
                .isTodaySolved(this.getIsTodaySolved())
                .isTodayRandomSolved(this.getIsTodayRandomSolved())
                .todaySolvedProblemCount(this.getTodaySolvedProblemCount())
                .maxRandomStreak(0)
                .build();
    }

    public UserInfoResponse toUserInfoResponse(Integer maxRandomStreak) {
        return UserInfoResponse.builder()
                .bojHandle(this.getBojHandle())
                .notionId(this.getNotionId())
                .manager(this.getManager())
                .warning(this.getWarning())
                .profileImg(this.getProfileImg())
                .emoji(this.getEmoji())
                .tier(this.getTier())
                .totalSolved(this.getTotalSolved())
                .currentStreak(this.getCurrentStreak())
                .currentRandomStreak(this.getCurrentRandomStreak())
                .team(this.getTeam())
                .point(this.getPoint())
                .isTodaySolved(this.getIsTodaySolved())
                .isTodayRandomSolved(this.getIsTodayRandomSolved())
                .todaySolvedProblemCount(this.getTodaySolvedProblemCount())
                .maxRandomStreak(maxRandomStreak)
                .build();
    }

    public void setScrapingUserInfo(UserScrapingInfoDto userInfo) {
        this.profileImg = userInfo.getProfileImg();
        this.tier = userInfo.getTier();
        this.totalSolved = userInfo.getTotalSolved();
        this.currentStreak = userInfo.getCurrentStreak();
        this.isTodaySolved = userInfo.getIsTodaySolved();
        this.todaySolvedProblemCount = userInfo.getTodaySolvedProblemCount();
    }

    public void setTeamNumber(Integer team) {
        this.team = team;
    }
}
