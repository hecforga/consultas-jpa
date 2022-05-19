package com.nunsys.consultas.repository.custom;

import com.nunsys.consultas.domain.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Post> findAllByTitleLike(Set<String> titleSet) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = cb.createQuery(Post.class);
        Root<Post> post = query.from(Post.class);

        Path<String> titlePath = post.get("title");

        List<Predicate> predicates = new ArrayList<>();
        for (String title : titleSet) {
            predicates.add(cb.like(cb.upper(titlePath), "%" + title.toUpperCase() + "%"));
        }
        query.select(post).where(cb.or(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
