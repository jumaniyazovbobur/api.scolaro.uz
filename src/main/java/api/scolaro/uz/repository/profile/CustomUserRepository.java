package api.scolaro.uz.repository.profile;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.profile.UserFilterDTO;
import api.scolaro.uz.entity.profile.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Repository
public class CustomUserRepository {

    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<UserEntity> filterPagination(UserFilterDTO dto, Integer page, Integer size) {
        StringBuilder selectBuilder=new StringBuilder("from UserEntity as u");
        StringBuilder countBuilder=new StringBuilder("select count(u) from UserEntity as u");
        StringBuilder builder=new StringBuilder(" where e.visible=true");
        Map<String,Object> params=new LinkedHashMap<>();
        if(dto.getName()!=null && !dto.getName().isBlank()){
            builder.append(" and u.name like :name");
            params.put("name","'%"+dto.getName()+"%'");
        }
        if(dto.getSurname()!=null && !dto.getSurname().isBlank()){
            builder.append(" and u.surname like :surname");
            params.put("surname","'%"+dto.getSurname()+"%'");
        }
        if(dto.getFromCreatedDate()!=null){
            builder.append(" and u.createdDate>=:from");
            params.put("from",LocalDateTime.of(dto.getFromCreatedDate(),LocalTime.MIN));
        }
        if(dto.getToCreatedDate()!=null){
            builder.append(" and u.createdDate<=:to");
            params.put("to",LocalDateTime.of(dto.getToCreatedDate(),LocalTime.MAX));
        }
        countBuilder.append(builder);
        builder.append(" order by u.createdDate ");
        selectBuilder.append(builder);
        Query selectQuery=entityManager.createQuery(selectBuilder.toString());
        Query countQuery=entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String,Object> p: params.entrySet()){
            selectQuery.setParameter(p.getKey(),p.getValue());
            countQuery.setParameter(p.getKey(),p.getValue());
        }
        selectQuery.setFirstResult(page*size);
        selectQuery.setMaxResults(size);
        List<UserEntity> entityList=selectQuery.getResultList();
        Long totalElement=(Long) countQuery.getSingleResult();
        return new FilterResultDTO<UserEntity>(entityList,totalElement);

    }
}
