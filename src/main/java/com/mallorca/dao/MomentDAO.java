package com.mallorca.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mallorca.entity.Moment;
import com.mallorca.entity.User;
import com.mallorca.entity.UserMomentState;

public interface MomentDAO extends JpaRepository<Moment, Integer> {

	@Query(value = "SELECT DISTINCT m.* FROM moment as m, user_moment as us WHERE (us.moment_id != m.id OR us.user_id != :userId) AND  (m.text  LIKE :first OR m.text LIKE :second) LIMIT :page, :offset", nativeQuery = true)
	public List<Moment> searchMoments(@Param("first") String first, @Param("second") String second, @Param("page") Integer page, @Param("offset") Integer offset, @Param("userId") Integer userId);

	@Query(value = "SELECT DISTINCT m.* FROM moment as m, user_moment as us WHERE us.moment_id != m.id OR us.user_id != :userId LIMIT :page, :offset", nativeQuery = true)
	public List<Moment> searchWithoutQuery(@Param("page") Integer page, @Param("offset") Integer offset, @Param("userId") Integer userId);

	@Query("SELECT um.moment FROM  UserMoment um WHERE um.user = :user AND um.state = :state ORDER BY um.modified DESC")
	public List<Moment> findByUserAndState(@Param("user") User user, @Param("state") UserMomentState state, Pageable pageable);

	@Query("SELECT count(um.moment) FROM  UserMoment um WHERE um.user = :user AND um.state = :state")
	public Long findByUserAndStateCount(@Param("user") User user, @Param("state") UserMomentState state);

	public Moment findFirstByCreatorOrderByIdDesc(User creator);

}
