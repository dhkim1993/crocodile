package com.crocodile.api.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@DynamicUpdate
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreationTimestamp
    @JsonFormat(timezone = "GMT+09:00", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date_time", nullable = false)
    protected LocalDateTime createdDateTime;

    @UpdateTimestamp
    @JsonFormat(timezone = "GMT+09:00", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_date_time", nullable = false)
    protected LocalDateTime updatedDateTime;
}
