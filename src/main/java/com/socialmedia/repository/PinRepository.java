package com.socialmedia.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socialmedia.model.Pins;
import com.socialmedia.model.Types;
import com.socialmedia.model.Users;

public interface PinRepository extends JpaRepository<Pins, Integer> {

    @Query("SELECT p FROM Pins p WHERE NOT EXISTS (SELECT 1 FROM Report_Pins rp WHERE rp.pin.id = p.id) AND p.user = :user")
    public List<Pins> findByUserOrderByIdDesc(Users user);
    
    @Query("SELECT p FROM Pins p WHERE NOT EXISTS (SELECT 1 FROM Report_Pins rp WHERE rp.pin.id = p.id)")
    public List<Pins> getAll();

    public List<Pins> findByType(Types type);

    public List<Pins> findByUser(Optional<Users> user);

    public long countByCreatedAtBefore(Date created_at);

    @Query("SELECT COUNT(e) FROM Pins e WHERE DATE_FORMAT(e.createdAt, '%Y-%m-%d') = :formattedDate")
    long countByCreatedAt(@Param("formattedDate") String formattedDate);

    @Query("SELECT COUNT(e) FROM Pins e WHERE DATE_FORMAT(e.createdAt, '%Y-%m-%d') >= :date1 AND DATE_FORMAT(e.createdAt, '%Y-%m-%d') <= :date2")
    long countByCreatedAt(@Param("date1") String date1, @Param("date2") String date2);

}
