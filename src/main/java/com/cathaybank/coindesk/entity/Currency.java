package com.cathaybank.coindesk.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "CURRENCY")
public class Currency {
    @Id
    @Column(name = "CODE")
    private String code;
    @Column(name = "CODE_ZH")
    private String codeZh;
}
