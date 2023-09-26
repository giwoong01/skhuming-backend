package com.itcontest.skhuming.notice.domain;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.MemberScrapNotice;
import com.itcontest.skhuming.notice.exception.NotScheduleFormatException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Entity
@Getter
public class Notice {

    private static final Pattern SCHEDULE_PATTERN = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}~\\d{4}-\\d{1,2}-\\d{1,2}");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    private String title;

    private String schedule;

    private String contents;

    private int mileageScore;

    private String links;

    private String createDate;

    @OneToMany(mappedBy = "notice")
    private List<MemberScrapNotice> member = new ArrayList<>();

    protected Notice() {
    }

    public Notice(String title, String schedule, String contents, int mileageScore, String createDate, String links) {
        notScheduleFormat(schedule);

        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.createDate = createDate;
        this.links = links;
    }

    public void update(String title, String schedule, String contents, int mileageScore, String createDate, String links) {
        notScheduleFormat(schedule);

        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.createDate = createDate;
        this.links = links;
    }

    public List<Member> getScrapMember() {
        return member.stream()
                .map(MemberScrapNotice::getMember)
                .collect(Collectors.toList());
    }

    private void notScheduleFormat(String schedule) {
        Matcher matcher = SCHEDULE_PATTERN.matcher(schedule);
        if (!matcher.matches()) {
            throw new NotScheduleFormatException();
        }
    }

}

