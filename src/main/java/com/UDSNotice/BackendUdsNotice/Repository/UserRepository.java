package com.UDSNotice.BackendUdsNotice.Repository;

import com.UDSNotice.BackendUdsNotice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
