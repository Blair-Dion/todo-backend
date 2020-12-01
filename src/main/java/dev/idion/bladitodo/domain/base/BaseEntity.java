package dev.idion.bladitodo.domain.base;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity extends BaseTimeEntity {

  @CreatedBy
  @Column(updatable = false)
  private Long createdBy;

  @LastModifiedBy
  private Long updatedBy;
}
