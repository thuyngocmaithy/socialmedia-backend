package com.socialmedia.repository;

import com.socialmedia.model.Report_Pins;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface Report_PinRepository extends JpaRepository<Report_Pins, Integer> {

    // Modify the query to use proper join clauses
    @Query("SELECT rp.id,  p.title AS pins, p.image, cr.content AS reportContent, u.username, rp.approve, rp.reject " +
            "FROM Report_Pins rp " +
            "JOIN rp.pin p " +
            "JOIN rp.content cr " +
            "JOIN rp.userRatify u")
    List<Object[]> findAllWithDetails();

    @Query("SELECT rp.id,  p.title AS pins, p.image, cr.content AS reportContent, u.username, rp.approve, rp.reject " +
            "FROM Report_Pins rp " +
            "JOIN rp.pin p " +
            "JOIN rp.content cr " +
            "JOIN rp.userRatify u " +
            "WHERE rp.id = :id")
    Object[] findDetailsById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Report_Comments rp SET rp.approve = :approve, rp.reject = :reject WHERE rp.id = :id")
    void updateApprovalStatus(@Param("id") int id, @Param("approve") Boolean approve, @Param("reject") Boolean reject);
}
