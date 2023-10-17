package api.scolaro.uz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "simple_message")
public class SimpleMessageEntity extends BaseEntity{

}
