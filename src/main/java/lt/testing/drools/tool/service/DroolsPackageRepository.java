package lt.testing.drools.tool.service;

import lt.testing.drools.tool.model.DroolsPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroolsPackageRepository extends JpaRepository<DroolsPackage, Long> {

    Optional<DroolsPackage> getByName(String packageName);
}
