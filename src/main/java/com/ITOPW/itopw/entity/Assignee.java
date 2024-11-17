package com.ITOPW.itopw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Assignee {
    @Id
    private String assigneeId;
    private String assigneeName;
    private String assigneeProfile;

}
