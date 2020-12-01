package dev.idion.bladitodo.domain.base;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
@MappedSuperclass
public abstract class BaseTimeEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDatetime;

  @LastModifiedDate
  private LocalDateTime updatedDatetime;

}
