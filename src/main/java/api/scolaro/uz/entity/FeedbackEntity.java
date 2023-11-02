package api.scolaro.uz.entity;

import api.scolaro.uz.enums.FeedBackType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity{
    @Column(name = "content")
    private String content;
    @Column(name = "person_id")
    private String personId;
    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type")
    private FeedBackType feedbackType;
}
