package lt.testing.drools.tool.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class PackageInfoResponse {

    private final List<DroolPackageRequest> packages;

    @JsonCreator
    public PackageInfoResponse(List<DroolPackageRequest> packages) {
        this.packages = packages;
    }

    public List<DroolPackageRequest> getPackages() {
        return packages;
    }

    @Override
    public String toString() {
        return "PackageInfoResponse{" +
                "packages=" + packages +
                '}';
    }
}
