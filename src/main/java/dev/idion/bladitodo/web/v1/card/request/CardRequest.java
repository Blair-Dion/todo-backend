package dev.idion.bladitodo.web.v1.card.request;

import dev.idion.bladitodo.domain.card.Card;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CardRequest {

  @NotBlank(message = "제목을 입력해주세요.")
  @Size(max = 30, message = "제목은 최대 30자만 입력가능합니다.")
  private String title;

  @NotBlank(message = "내용을 입력해주세요.")
  @Size(max = 500, message = "내용은 최대 500자만 입력가능합니다.")
  private String contents;

  public Card toEntity() {
    return Card.builder().withTitle(title).withContents(contents).build();
  }
}
