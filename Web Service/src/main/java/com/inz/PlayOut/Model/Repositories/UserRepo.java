package com.inz.PlayOut.Model.Repositories;

import com.inz.PlayOut.Model.Entites.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<AppUser, Long> {
}
