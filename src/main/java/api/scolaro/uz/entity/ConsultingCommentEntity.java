package api.scolaro.uz.entity;

import api.scolaro.uz.entity.consulting.ConsultingEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consulting_comment")
@Getter
@Setter
public class ConsultingCommentEntity extends BaseEntity {
    @Column(name = "content")
    private String content;

    @Column(name = "consulting_id")
    private String consultingId;
    @ManyToOne
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;

    @Column(name = "student_id")
    private String studentId;
    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private ProfileEntity student;


    // consultingId
    // studentId
    // content (text)

    // Student qaysidir consulting xizmatidan foydalangandna keyin shu consolting page da
    // u haqida qandaydir text (comment) yozishi mumkun.
    //  ConsultingComment ni faqat student role ga ega bo'lgan Profile create qiladi.

    // quyidagi api lar qilinsin
    //  1. POST  api/v1/consulting/comment/{consultingId} Create (consultingId, content) (studentId - jwtdan olinsin.)
    //  Shu student va consulting o'rtasida AppApplicationEntity (ariza) bor bo'lishi kerak.
    // va arizaning statusi FINISHED bo'lishi kerak.
    // 2. GET api/v1/consulting/comment/{consultingId}  -  Get Consulting comment list - consulting id si bo'yicha
    // shu consultingda yozilgan commentlarni yuboradi. visible = true bo'lganlarni
    // Response (comment{id,createdDate,content, student{id,name,surname, photo{id,url}}}
    // Bu open api permitAll bo'lsin.
    // 3. GET api/v1/consulting/comment/filter - commentlarni filter qilish uchun. Only for admin
    //  filter{studentId,consultingId,createdDate}.
    //  Response (comment{id,createdDate,content, student{id,name,surname, photo{id,url}}, consulting{id,name, photo{id,url}}}

}
